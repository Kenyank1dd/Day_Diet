package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    /**
     * 文章id
     */
    private long article_id;
    /**
     * 内容来源
     */
    private String article_src;
    /**
     * 文章标题
     */
    private String article_title;
    /**
     * 文章html
     */
    private String content;
    /**
     * 封面图url
     */
    private String pic_url;
}
