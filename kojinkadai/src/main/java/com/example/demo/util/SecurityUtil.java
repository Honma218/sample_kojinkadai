package com.example.demo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * セキュリティユーティリティクラス
 * 
 * Spring Securityの認証情報を簡単に取得するためのユーティリティメソッドを提供します。
 * 
 * @author Generated with Claude Code
 * @version 1.0
 */
public final class SecurityUtil {
    
    /** プライベートコンストラクタ（インスタンス化を防ぐ） */
    private SecurityUtil() {
        throw new UnsupportedOperationException("ユーティリティクラスはインスタンス化できません");
    }
    
    /**
     * 現在の認証情報を取得
     * 
     * @return 認証情報のOptional
     */
    public static Optional<Authentication> getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication);
    }
    
    /**
     * 現在のユーザー詳細情報を取得
     * 
     * @return ユーザー詳細情報のOptional
     */
    public static Optional<UserDetails> getCurrentUserDetails() {
        return getCurrentAuthentication()
                .filter(auth -> auth.getPrincipal() instanceof UserDetails)
                .map(auth -> (UserDetails) auth.getPrincipal());
    }
    
    /**
     * 現在のユーザー名を取得
     * 
     * @return ユーザー名のOptional
     */
    public static Optional<String> getCurrentUsername() {
        return getCurrentUserDetails()
                .map(UserDetails::getUsername);
    }
    
    /**
     * ユーザーが認証済みかどうかを判定
     * 
     * @return 認証済みの場合true
     */
    public static boolean isAuthenticated() {
        return getCurrentAuthentication()
                .map(Authentication::isAuthenticated)
                .orElse(false);
    }
    
    /**
     * ユーザーが指定したロールを持っているかを判定
     * 
     * @param role ロール名（ROLE_プレフィックスなし）
     * @return ロールを持っている場合true
     */
    public static boolean hasRole(String role) {
        return getCurrentAuthentication()
                .map(auth -> auth.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role)))
                .orElse(false);
    }
    
    /**
     * ユーザーが指定した権限を持っているかを判定
     * 
     * @param authority 権限名
     * @return 権限を持っている場合true
     */
    public static boolean hasAuthority(String authority) {
        return getCurrentAuthentication()
                .map(auth -> auth.getAuthorities().stream()
                        .anyMatch(grantedAuth -> grantedAuth.getAuthority().equals(authority)))
                .orElse(false);
    }
    
    /**
     * 現在のユーザーが指定したユーザーかどうかを判定
     * 
     * @param username 比較対象のユーザー名
     * @return 同じユーザーの場合true
     */
    public static boolean isCurrentUser(String username) {
        return getCurrentUsername()
                .map(currentUsername -> currentUsername.equals(username))
                .orElse(false);
    }
    
    /**
     * 現在のユーザーが管理者かどうかを判定
     * 
     * @return 管理者の場合true
     */
    public static boolean isAdmin() {
        return hasRole("ADMIN");
    }
}