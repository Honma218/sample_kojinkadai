package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserRepository;
import com.example.demo.application.form.UserForm;
import com.example.demo.application.usecase.UserAuthUsecase;
import com.example.demo.application.usecase.UserProfileUseCase;
import com.example.demo.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ユーザーサービス実装クラス
 * 
 * ユーザー管理に関するビジネスロジックを実装します。
 * 既存のUseCaseを活用しつつ、新しいビジネスルールを追加します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserAuthUsecase userAuthUsecase;
    private final UserProfileUseCase userProfileUseCase;
    private final UserRepository userRepository;
    
    @Override
    public void createUser(UserForm userForm, HttpServletRequest request) throws BusinessException {
        log.info("新規ユーザー作成開始: username={}", userForm.getUsername());
        
        // ユーザー名の重複チェック
        if (!isUsernameAvailable(userForm.getUsername())) {
            throw BusinessException.validationError(
                String.format("ユーザー名'%s'は既に使用されています", userForm.getUsername())
            );
        }
        
        try {
            // 既存のUseCaseを利用してユーザー作成
            userAuthUsecase.userCreate(userForm, request);
            log.info("新規ユーザー作成完了: username={}", userForm.getUsername());
            
        } catch (IllegalArgumentException e) {
            log.warn("ユーザー作成でバリデーションエラー: username={}, error={}", 
                     userForm.getUsername(), e.getMessage());
            throw BusinessException.validationError(e.getMessage());
            
        } catch (Exception e) {
            log.error("ユーザー作成で予期しないエラー: username={}", 
                      userForm.getUsername(), e);
            throw new BusinessException(
                "ユーザー作成に失敗しました: " + e.getMessage(),
                "ユーザー作成に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) throws BusinessException {
        log.debug("ユーザー名でユーザー取得: username={}", username);
        
        try {
            User user = userProfileUseCase.getUserProfileByUsername(username);
            return user;
            
        } catch (IllegalArgumentException e) {
            log.warn("ユーザー取得でエラー: username={}, error={}", username, e.getMessage());
            throw BusinessException.notFound(e.getMessage());
        } catch (Exception e) {
            log.error("ユーザー取得でエラー: username={}", username, e);
            throw new BusinessException(
                "ユーザー情報の取得に失敗しました: " + e.getMessage(),
                "ユーザー情報の取得に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserById(String userId) throws BusinessException {
        log.debug("ユーザーIDでユーザー取得: userId={}", userId);
        
        try {
            User user = userProfileUseCase.getUserProfile(userId);
            return user;
            
        } catch (IllegalArgumentException e) {
            log.warn("ユーザー取得でエラー: userId={}, error={}", userId, e.getMessage());
            throw BusinessException.notFound(e.getMessage());
        } catch (Exception e) {
            log.error("ユーザー取得でエラー: userId={}", userId, e);
            throw new BusinessException(
                "ユーザー情報の取得に失敗しました: " + e.getMessage(),
                "ユーザー情報の取得に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    public void updateProfile(String userId, String displayName, String bio) throws BusinessException {
        log.info("プロフィール更新開始: userId={}", userId);
        
        // ユーザーの存在確認
        getUserById(userId); // 存在しない場合は例外がスローされる
        
        try {
            userProfileUseCase.updateProfile(userId, displayName, bio);
            log.info("プロフィール更新完了: userId={}", userId);
            
        } catch (Exception e) {
            log.error("プロフィール更新でエラー: userId={}", userId, e);
            throw new BusinessException(
                "プロフィールの更新に失敗しました: " + e.getMessage(),
                "プロフィールの更新に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        try {
            return userRepository.findByUsername(username).isEmpty();
        } catch (Exception e) {
            log.warn("ユーザー名重複チェックでエラー: username={}", username, e);
            // エラーの場合は安全側に倒して利用不可とする
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        // UserRepositoryにfindByEmailメソッドがないため、
        // 現在は常にtrueを返す（実装は後で追加）
        return true;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> searchByDisplayName(String keyword) {
        log.debug("表示名で検索: keyword={}", keyword);
        
        try {
            return userRepository.searchByDisplayName(keyword);
        } catch (Exception e) {
            log.error("検索でエラー: keyword={}", keyword, e);
            throw new BusinessException(
                "検索に失敗しました: " + e.getMessage(),
                "検索に失敗しました",
                500,
                e
            );
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.debug("全ユーザー取得");
        
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("全ユーザー取得でエラー", e);
            throw new BusinessException(
                "ユーザー一覧の取得に失敗しました: " + e.getMessage(),
                "ユーザー一覧の取得に失敗しました",
                500,
                e
            );
        }
    }
}