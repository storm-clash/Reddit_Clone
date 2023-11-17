package com.example.BaseProject.repositories;

import com.example.BaseProject.model.Post;
import com.example.BaseProject.model.Subreddit;
import com.example.BaseProject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
