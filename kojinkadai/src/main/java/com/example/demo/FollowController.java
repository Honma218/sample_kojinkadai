package com.example.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.application.usecase.FollowUseCase;
import com.example.demo.application.usecase.UserProfileUseCase;
import com.example.demo.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * フォロー機能管理コントローラー
 * 
 * ユーザー間のフォロー関係を管理し、フォロー・アンフォロー機能、
 * フォロー中・フォロワーリストの表示機能を提供します。
 * 全ての操作は認証済みユーザーのみアクセス可能で、安全なエラーハンドリングを実装しています。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowUseCase followUseCase;
    private final UserProfileUseCase userProfileUseCase;

    /**
     * フォロー機能のメインページを表示
     * フォロー中のユーザー一覧画面にリダイレクトします。
     * 
     * @param userDetails 現在ログイン中のユーザー情報
     * @return フォロー中ユーザー一覧画面のModelAndView
     */
    @GetMapping
    public ModelAndView followPage(@AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーがフォローページにアクセスしようとしました");
            return new ModelAndView("redirect:/user");
        }
        
        // フォロー中ユーザー一覧へリダイレクト
        return viewFollowing(userDetails);
    }

    /**
     * 指定されたユーザーをフォロー
     * AJAX経由で呼び出されるRESTエンドポイントです。
     * 
     * @param userId フォロー対象のユーザーID
     * @param userDetails 現在ログイン中のユーザー情報
     * @return フォロー結果のメッセージを含むResponseEntity
     */
    @PostMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<String> follow(@PathVariable String userId, 
                                       @AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーがフォロー操作を試行しました: targetUserId={}", userId);
            return ResponseEntity.status(401).body("認証が必要です");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            
            // フォロー処理を実行
            followUseCase.follow(currentUserId, userId);
            
            log.info("フォロー処理が正常に完了しました: follower={}, following={}", 
                     userDetails.getUsername(), userId);
            return ResponseEntity.ok("フォローしました");
            
        } catch (IllegalArgumentException e) {
            log.warn("フォロー処理でパラメータエラーが発生しました: user={}, targetUserId={}, error={}", 
                     userDetails.getUsername(), userId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("フォロー処理で予期しないエラーが発生しました: user={}, targetUserId={}", 
                      userDetails.getUsername(), userId, e);
            return ResponseEntity.internalServerError().body("フォローに失敗しました");
        }
    }

    /**
     * 指定されたユーザーのフォローを解除
     * AJAX経由で呼び出されるRESTエンドポイントです。
     * 
     * @param userId フォロー解除対象のユーザーID
     * @param userDetails 現在ログイン中のユーザー情報
     * @return フォロー解除結果のメッセージを含むResponseEntity
     */
    @PostMapping("/unfollow/{userId}")
    @ResponseBody
    public ResponseEntity<String> unfollow(@PathVariable String userId, 
                                         @AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーがアンフォロー操作を試行しました: targetUserId={}", userId);
            return ResponseEntity.status(401).body("認証が必要です");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            
            // アンフォロー処理を実行
            followUseCase.unfollow(currentUserId, userId);
            
            log.info("アンフォロー処理が正常に完了しました: follower={}, unfollowed={}", 
                     userDetails.getUsername(), userId);
            return ResponseEntity.ok("フォローを解除しました");
            
        } catch (IllegalArgumentException e) {
            log.warn("アンフォロー処理でパラメータエラーが発生しました: user={}, targetUserId={}, error={}", 
                     userDetails.getUsername(), userId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("アンフォロー処理で予期しないエラーが発生しました: user={}, targetUserId={}", 
                      userDetails.getUsername(), userId, e);
            return ResponseEntity.internalServerError().body("フォロー解除に失敗しました");
        }
    }

    /**
     * フォロー中のユーザー一覧を表示
     * 
     * @param userDetails 現在ログイン中のユーザー情報
     * @return フォロー中ユーザー一覧画面のModelAndView
     */
    @GetMapping("/following")
    public ModelAndView viewFollowing(@AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーがフォロー中ユーザー一覧にアクセスしようとしました");
            return new ModelAndView("redirect:/user");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            User currentUser = userProfileUseCase.getUserProfileByUsername(userDetails.getUsername());
            
            // フォロー中のユーザーリストを取得
            List<User> followingUsers = followUseCase.getFollowingUsers(currentUserId);
            
            // ModelAndViewを構築
            ModelAndView modelAndView = new ModelAndView("follow/following");
            modelAndView.addObject("users", followingUsers);
            modelAndView.addObject("currentUser", currentUser);
            
            log.debug("フォロー中ユーザー一覧を表示しました: user={}, followingCount={}", 
                      userDetails.getUsername(), followingUsers.size());
            return modelAndView;
            
        } catch (Exception e) {
            log.error("フォロー中ユーザー取得でエラーが発生しました: user={}", 
                      userDetails.getUsername(), e);
            return new ModelAndView("redirect:/profile");
        }
    }

    /**
     * フォロワー一覧を表示
     * 
     * @param userDetails 現在ログイン中のユーザー情報
     * @return フォロワー一覧画面のModelAndView
     */
    @GetMapping("/followers")
    public ModelAndView viewFollowers(@AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーがフォロワー一覧にアクセスしようとしました");
            return new ModelAndView("redirect:/user");
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            User currentUser = userProfileUseCase.getUserProfileByUsername(userDetails.getUsername());
            
            // フォロワーリストを取得
            List<User> followerUsers = followUseCase.getFollowerUsers(currentUserId);
            
            // ModelAndViewを構築
            ModelAndView modelAndView = new ModelAndView("follow/followers");
            modelAndView.addObject("users", followerUsers);
            modelAndView.addObject("currentUser", currentUser);
            
            log.debug("フォロワー一覧を表示しました: user={}, followerCount={}", 
                      userDetails.getUsername(), followerUsers.size());
            return modelAndView;
            
        } catch (Exception e) {
            log.error("フォロワー取得でエラーが発生しました: user={}", 
                      userDetails.getUsername(), e);
            return new ModelAndView("redirect:/profile");
        }
    }

    /**
     * 指定されたユーザーをフォローしているかどうかの状態を取得
     * AJAX経由で呼び出されるRESTエンドポイントです。
     * 
     * @param userId 状態確認対象のユーザーID
     * @param userDetails 現在ログイン中のユーザー情報
     * @return フォロー状態（true: フォロー中、false: 未フォロー）を含むResponseEntity
     */
    @GetMapping("/status/{userId}")
    @ResponseBody
    public ResponseEntity<Boolean> getFollowStatus(@PathVariable String userId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        // 認証状態の確認
        if (!isAuthenticated(userDetails)) {
            log.warn("未認証ユーザーがフォロー状態確認を試行しました: targetUserId={}", userId);
            return ResponseEntity.status(401).build();
        }
        
        try {
            // 現在のユーザー情報を安全に取得
            String currentUserId = getCurrentUserIdSafely(userDetails);
            
            // フォロー状態を確認
            boolean isFollowing = followUseCase.isFollowing(currentUserId, userId);
            
            log.debug("フォロー状態を確認しました: user={}, targetUserId={}, isFollowing={}", 
                      userDetails.getUsername(), userId, isFollowing);
            return ResponseEntity.ok(isFollowing);
            
        } catch (Exception e) {
            log.error("フォロー状態取得でエラーが発生しました: user={}, targetUserId={}", 
                      userDetails.getUsername(), userId, e);
            return ResponseEntity.internalServerError().build();
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
}