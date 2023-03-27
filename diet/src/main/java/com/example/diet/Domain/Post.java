package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    //社区动态表
    private long post_id;   //动态id
    private long author_id;  //动态发布作者的id
    private String post_title;  //动态标题
    private String post_time;  //发布时间
    private long post_type;  //动态类型
    private String picture_url;   //图片保存url
    private String video_url;    //视频保存url
    private long post_like;    //点赞数
    private long posy_view;    //浏览数

}
