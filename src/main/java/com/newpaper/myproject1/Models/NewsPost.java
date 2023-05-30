package com.newpaper.myproject1.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class NewsPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private LocalDate date;
    private String text;
    private String image;

//    @OneToMany(mappedBy = "newsPost", cascade = CascadeType.ALL)
//    private List<CommentsNews> commentsNews = new ArrayList<>();

    public NewsPost() {
    }

    public NewsPost(String title, String text, String image) {
        this.title = title;
        this.date = LocalDate.now();
        this.text = text;
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) { this.image = image; }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getImage() { return image; }

}
