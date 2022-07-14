package com.example.myblog.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByArticlesIdOrderByCreatedAtDesc(Long articlesId);

}
