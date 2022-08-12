package com.example.demo.DTO;


public class Post {
    int postid;
    String nickname;
    String title;
    public Post(int id, String name, String title){
        id=postid;
        nickname=name;
        this.title=title;
    }

    public String getNickName() {
        return nickname;
    }
    public int getPostId() {
        return postid;
    }
    public String getTitle() {
        return title;
    }
    
}
