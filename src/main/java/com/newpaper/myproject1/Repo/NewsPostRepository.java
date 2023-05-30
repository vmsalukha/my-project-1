package com.newpaper.myproject1.Repo;

import com.newpaper.myproject1.Models.NewsPost;
import org.springframework.data.repository.CrudRepository;

public interface NewsPostRepository extends CrudRepository<NewsPost, Long> {
}
