package com.newpaper.myproject1.Repo;

import com.newpaper.myproject1.Models.CommentsNews;
import com.newpaper.myproject1.Models.NewsPost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentsNewsRepository extends CrudRepository <CommentsNews, Long> {
    List<CommentsNews> findByNewsPost_id(Long newsPost_id);
}
