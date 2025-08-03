package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ページングレスポンスクラス
 * 
 * ページング機能付きのAPIレスポンスを提供します。
 * 大量データの取得時にクライアント側でのページング処理を支援します。
 * 
 * @param <T> リストアイテムの型
 * @author Generated with Claude Code
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    
    /** データリスト */
    private List<T> items;
    
    /** ページング情報 */
    private PageInfo pageInfo;
    
    /**
     * ページング情報クラス
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        /** 現在のページ番号（0から開始） */
        private int currentPage;
        
        /** 1ページあたりのアイテム数 */
        private int pageSize;
        
        /** 総アイテム数 */
        private long totalItems;
        
        /** 総ページ数 */
        private int totalPages;
        
        /** 前のページが存在するか */
        private boolean hasPrevious;
        
        /** 次のページが存在するか */
        private boolean hasNext;
        
        /**
         * ページング情報を生成
         * 
         * @param currentPage 現在のページ番号
         * @param pageSize 1ページあたりのアイテム数
         * @param totalItems 総アイテム数
         * @return ページング情報
         */
        public static PageInfo of(int currentPage, int pageSize, long totalItems) {
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            
            return PageInfo.builder()
                    .currentPage(currentPage)
                    .pageSize(pageSize)
                    .totalItems(totalItems)
                    .totalPages(totalPages)
                    .hasPrevious(currentPage > 0)
                    .hasNext(currentPage < totalPages - 1)
                    .build();
        }
    }
    
    /**
     * ページングレスポンスを生成
     * 
     * @param items データリスト
     * @param currentPage 現在のページ番号
     * @param pageSize 1ページあたりのアイテム数
     * @param totalItems 総アイテム数
     * @param <T> アイテムの型
     * @return ページングレスポンス
     */
    public static <T> PagedResponse<T> of(List<T> items, int currentPage, int pageSize, long totalItems) {
        return PagedResponse.<T>builder()
                .items(items)
                .pageInfo(PageInfo.of(currentPage, pageSize, totalItems))
                .build();
    }
}