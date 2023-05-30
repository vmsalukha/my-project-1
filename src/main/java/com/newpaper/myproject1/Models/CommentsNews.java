package com.newpaper.myproject1.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class CommentsNews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String text;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "newsPost_id", referencedColumnName = "id")
    private NewsPost newsPost;

    public CommentsNews() {
    }

    public CommentsNews(String userName, String text, NewsPost newsPost) {
        this.userName = userName;
        this.text = text;
        this.date = LocalDate.now();
        this.newsPost = newsPost;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public NewsPost getNewsPost() {
        return newsPost;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNewsPost(NewsPost newsPost) {
        this.newsPost = newsPost;
    }
}
