package com.example.diet.Domain;

import java.util.List;

public class Post_User {
    private List<Post> postList;
    private List<User> userList;

    public Post_User(List<Post> postList, List<User> userList) {
        this.postList = postList;
        this.userList = userList;
    }
}
