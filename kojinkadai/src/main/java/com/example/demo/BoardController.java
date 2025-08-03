package com.example.demo;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.application.dto.PostWithUserDto;
import com.example.demo.application.form.ContentForm;
import com.example.demo.application.usecase.PostUseCase;
import com.example.demo.application.usecase.UserContentUseCase;
import com.example.demo.application.usecase.UserProfileUseCase;
import com.example.demo.domain.model.Post;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserContents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ボード（タイムライン）管理コントローラー
 * 
 * ソーシャルメディア風のタイムライン機能を提供し、投稿の作成・表示・編集・削除機能を管理します。
 * フォローしているユーザーの投稿と自分の投稿を時系列で表示するタイムライン機能が主要な機能です。
 * 後方互換性のため、従来のコメントシステムもサポートしています。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    /** 後方互換性のための旧コメントシステム */
    private final UserContentUseCase userContentUseCase;
    
    /** 新しい投稿システムのユースケース */
    private final PostUseCase postUseCase;
    
    /** ユーザープロフィール関連のユースケース */
    private final UserProfileUseCase userProfileUseCase;

    /**
     * メインのタイムライン画面を表示
     * フォローしているユーザーの投稿と自分の投稿を時系列で表示します。
     * 後方互換性のため、従来のコメントシステムも併用しています。
     * 
     * @param userDetails 現在ログイン中のユーザー情報
     * @return タイムライン表示画面のModelAndView
     */
    @GetMapping
    public ModelAndView viewBoard(@AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーがボード画面にアクセスしようとしました");
            return new ModelAndView("redirect:/user");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            User currentUser = userProfileUseCase.getUserProfileByUsername(userDetails.getUsername());
            
            // タイムラインデータを取得
            List<PostWithUserDto> timeline = postUseCase.getTimeline(currentUserId);
            
            // 後方互換性のためのコメントデータを取得
            UserContents userContents = userContentUseCase.read();
            
            // ModelAndViewを構築
            ModelAndView modelAndView = createBoardView(timeline, userContents, currentUser);
            
            log.debug("ボード画面を表示しました: user={}, timelineCount={}", 
                      userDetails.getUsername(), timeline.size());
            return modelAndView;
            
        } catch (Exception e) {
            log.error("ボード表示でエラーが発生しました: user={}", userDetails.getUsername(), e);
            return new ModelAndView("redirect:/user");
        }
    }

    /**
     * 新しい投稿を作成
     * バリデーション済みの投稿内容を受け取り、新しい投稿としてシステムに保存します。
     * 
     * @param userDetails 現在ログイン中のユーザー情報
     * @param contentForm バリデーション済みの投稿フォーム
     * @param bindingResult バリデーション結果
     * @return 投稿後のリダイレクト先またはエラー時の表示画面のModelAndView
     */
    @PostMapping
    public ModelAndView postContent(
            @AuthenticationPrincipal UserDetails userDetails,
            @Validated @ModelAttribute ContentForm contentForm,
            BindingResult bindingResult) {
        
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーが投稿作成を試行しました");
            return new ModelAndView("redirect:/user");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            User currentUser = userProfileUseCase.getUserProfileByUsername(userDetails.getUsername());
            
            // バリデーションエラーがある場合はボード画面に戻る
            if (bindingResult.hasErrors()) {
                log.debug("投稿作成でバリデーションエラーが発生しました: user={}", userDetails.getUsername());
                return createBoardViewWithErrors(currentUserId, currentUser, contentForm);
            }
    
            // 新しい投稿を作成
            postUseCase.createPost(currentUserId, contentForm.getText());
            
            log.info("新しい投稿が作成されました: user={}, content={}", 
                     userDetails.getUsername(), contentForm.getText().substring(0, Math.min(50, contentForm.getText().length())));
            return new ModelAndView("redirect:/board");
            
        } catch (Exception e) {
            log.error("投稿作成でエラーが発生しました: user={}", userDetails.getUsername(), e);
            return new ModelAndView("redirect:/board");
        }
    }

    /**
     * 投稿編集画面を表示
     * 投稿者本人のみがアクセス可能で、既存の投稿内容をフォームに設定して表示します。
     * 
     * @param id 編集対象の投稿ID
     * @param userDetails 現在ログイン中のユーザー情報
     * @return 投稿編集画面のModelAndView
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editPost(@PathVariable String id, 
                               @AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーが投稿編集画面にアクセスしようとしました: postId={}", id);
            return new ModelAndView("redirect:/user");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            
            // 編集対象の投稿を取得
            Post post = postUseCase.getPost(id);
            
            // 投稿者本人であることを確認
            if (!isPostOwner(post, currentUserId)) {
                log.warn("投稿者以外のユーザーが編集を試行しました: user={}, postId={}, postOwner={}", 
                         userDetails.getUsername(), id, post.getUserId().asString());
                return new ModelAndView("redirect:/board");
            }
            
            // 編集フォームを初期化
            ContentForm contentForm = initializeEditForm(post);
            
            ModelAndView modelAndView = new ModelAndView("board/edit");
            modelAndView.addObject("contentForm", contentForm);
            modelAndView.addObject("post", post);
            
            log.debug("投稿編集画面を表示しました: user={}, postId={}", userDetails.getUsername(), id);
            return modelAndView;
            
        } catch (Exception e) {
            log.error("投稿編集画面表示でエラーが発生しました: user={}, postId={}", 
                      userDetails.getUsername(), id, e);
            return new ModelAndView("redirect:/board");
        }
    }

    /**
     * 投稿を更新
     * バリデーション済みの更新内容を受け取り、既存の投稿を更新します。
     * 投稿者本人のみが更新可能です。
     * 
     * @param id 更新対象の投稿ID
     * @param contentForm バリデーション済みの投稿フォーム
     * @param bindingResult バリデーション結果
     * @param userDetails 現在ログイン中のユーザー情報
     * @return 更新後のリダイレクト先またはエラー時の表示画面のModelAndView
     */
    @PostMapping("/edit/{id}")
    public ModelAndView updatePost(@PathVariable String id,
                                 @Validated @ModelAttribute ContentForm contentForm,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーが投稿更新を試行しました: postId={}", id);
            return new ModelAndView("redirect:/user");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            
            // バリデーションエラーがある場合は編集画面に戻る
            if (bindingResult.hasErrors()) {
                log.debug("投稿更新でバリデーションエラーが発生しました: user={}, postId={}", 
                          userDetails.getUsername(), id);
                return createEditViewWithErrors(id, contentForm);
            }
    
            // 投稿を更新
            postUseCase.updatePost(id, contentForm.getText(), currentUserId);
            
            log.info("投稿が更新されました: user={}, postId={}", userDetails.getUsername(), id);
            return new ModelAndView("redirect:/board");
            
        } catch (Exception e) {
            log.error("投稿更新でエラーが発生しました: user={}, postId={}", 
                      userDetails.getUsername(), id, e);
            return new ModelAndView("redirect:/board");
        }
    }

    /**
     * 投稿を削除
     * 投稿者本人のみが削除可能で、指定された投稿をシステムから完全に削除します。
     * 
     * @param id 削除対象の投稿ID
     * @param userDetails 現在ログイン中のユーザー情報
     * @return 削除後のリダイレクト先のModelAndView
     */
    @PostMapping("/delete/{id}")
    public ModelAndView deletePost(@PathVariable String id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーが投稿削除を試行しました: postId={}", id);
            return new ModelAndView("redirect:/user");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            
            // 投稿を削除
            postUseCase.deletePost(id, currentUserId);
            
            log.info("投稿が削除されました: user={}, postId={}", userDetails.getUsername(), id);
            return new ModelAndView("redirect:/board");
            
        } catch (Exception e) {
            log.error("投稿削除でエラーが発生しました: user={}, postId={}", 
                      userDetails.getUsername(), id, e);
            return new ModelAndView("redirect:/board");
        }
    }
    
    /**
     * 認証状態を確認
     * 
     * @param userDetails Spring Securityから取得したユーザー情報
     * @return 認証済みの場合true
     */
    private boolean isAuthenticated(UserDetails userDetails) {
        return userDetails != null;
    }
    
    /**
     * 現在のユーザーIDを安全に取得
     * 
     * @param userDetails 認証済みのユーザー情報
     * @return ユーザーID文字列
     * @throws IllegalArgumentException ユーザーIDが無効な場合
     */
    private String getCurrentUserIdSafely(UserDetails userDetails) {
        User currentUser = userProfileUseCase.getUserProfileByUsername(userDetails.getUsername());
        
        if (currentUser == null || currentUser.getId() == null || currentUser.getId().getValue() == null) {
            log.error("現在のユーザー情報が不正です: ユーザー名={}", userDetails.getUsername());
            throw new IllegalArgumentException("ユーザー情報の取得に失敗しました");
        }
        
        return currentUser.getId().asString();
    }
    
    /**
     * ボード表示用のModelAndViewを作成
     * 
     * @param timeline タイムライン投稿リスト
     * @param userContents 従来のコメントデータ
     * @param currentUser 現在のユーザー
     * @return ボード表示用のModelAndView
     */
    private ModelAndView createBoardView(List<PostWithUserDto> timeline, 
                                       UserContents userContents, User currentUser) {
        ModelAndView modelAndView = new ModelAndView("board");
        modelAndView.addObject("timeline", timeline);
        modelAndView.addObject("contents", userContents.getValues());
        modelAndView.addObject("contentForm", new ContentForm());
        modelAndView.addObject("currentUser", currentUser);
        
        return modelAndView;
    }
    
    /**
     * エラーがあるボード表示用のModelAndViewを作成
     * 
     * @param currentUserId 現在のユーザーID
     * @param currentUser 現在のユーザー
     * @param contentForm エラーを含むフォーム
     * @return エラーがあるボード表示用のModelAndView
     */
    private ModelAndView createBoardViewWithErrors(String currentUserId, User currentUser, 
                                                  ContentForm contentForm) {
        List<PostWithUserDto> timeline = postUseCase.getTimeline(currentUserId);
        UserContents userContents = userContentUseCase.read();
        
        ModelAndView modelAndView = new ModelAndView("board");
        modelAndView.addObject("timeline", timeline);
        modelAndView.addObject("contents", userContents.getValues());
        modelAndView.addObject("contentForm", contentForm);
        modelAndView.addObject("currentUser", currentUser);
        
        return modelAndView;
    }
    
    /**
     * 投稿の所有者かどうかを確認
     * 
     * @param post 確認対象の投稿
     * @param currentUserId 現在のユーザーID
     * @return 投稿者本人の場合true
     */
    private boolean isPostOwner(Post post, String currentUserId) {
        return post.getUserId().asString().equals(currentUserId);
    }
    
    /**
     * 投稿編集用のフォームを初期化
     * 
     * @param post 編集対象の投稿
     * @return 初期化された編集フォーム
     */
    private ContentForm initializeEditForm(Post post) {
        ContentForm contentForm = new ContentForm();
        contentForm.setText(post.getContent().getValue());
        return contentForm;
    }
    
    /**
     * エラーがある編集画面用のModelAndViewを作成
     * 
     * @param postId 編集対象の投稿ID
     * @param contentForm エラーを含むフォーム
     * @return エラーがある編集画面用のModelAndView
     */
    private ModelAndView createEditViewWithErrors(String postId, ContentForm contentForm) {
        Post post = postUseCase.getPost(postId);
        ModelAndView modelAndView = new ModelAndView("board/edit");
        modelAndView.addObject("contentForm", contentForm);
        modelAndView.addObject("post", post);
        return modelAndView;
    }
}
