package com.example.demo.service;

import com.example.demo.domain.model.User;
import com.example.demo.application.form.UserForm;
import com.example.demo.application.form.ProfileForm;
import com.example.demo.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * ユーザーサービスインターfaces
 * 
 * ユーザー管理に関するビジネスロジックを定義します。
 * 認証、登録、プロフィール管理などの機能を提供します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public interface UserService {
    
    /**
     * 新規ユーザーを作成
     * 
     * @param userForm ユーザー登録フォーム
     * @param request HTTPリクエスト
     * @throws BusinessException ユーザー名やメールアドレスが既に存在する場合
     */
    void createUser(UserForm userForm, HttpServletRequest request) throws BusinessException;
    
    /**
     * ユーザー名でユーザーを取得
     * 
     * @param username ユーザー名
     * @return ユーザー情報
     * @throws BusinessException ユーザーが見つからない場合
     */
    User getUserByUsername(String username) throws BusinessException;
    
    /**
     * ユーザーIDでユーザーを取得
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     * @throws BusinessException ユーザーが見つからない場合
     */
    User getUserById(String userId) throws BusinessException;
    
    /**
     * プロフィールを更新
     * 
     * @param userId ユーザーID
     * @param displayName 表示名
     * @param bio 自己紹介
     * @throws BusinessException ユーザーが見つからない場合
     */
    void updateProfile(String userId, String displayName, String bio) throws BusinessException;
    
    /**
     * ユーザー名が使用可能かチェック
     * 
     * @param username ユーザー名
     * @return 使用可能な場合true
     */
    boolean isUsernameAvailable(String username);
    
    /**
     * メールアドレスが使用可能かチェック
     * 
     * @param email メールアドレス
     * @return 使用可能な場合true
     */
    boolean isEmailAvailable(String email);
    
    /**
     * 表示名で検索
     * 
     * @param keyword 検索キーワード
     * @return 検索結果のユーザーリスト
     */
    java.util.List<User> searchByDisplayName(String keyword);
    
    /**
     * 全ユーザーを取得
     * 
     * @return 全ユーザーのリスト
     */
    java.util.List<User> getAllUsers();
}