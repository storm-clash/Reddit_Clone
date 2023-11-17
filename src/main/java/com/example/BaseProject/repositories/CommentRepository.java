package com.example.BaseProject.repositories;

import com.example.BaseProject.dto.CommentDto;
import com.example.BaseProject.model.Comment;
import com.example.BaseProject.model.Post;
import com.example.BaseProject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
