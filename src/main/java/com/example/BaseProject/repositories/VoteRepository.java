package com.example.BaseProject.repositories;

import com.example.BaseProject.model.Post;
import com.example.BaseProject.model.Vote;
import com.example.BaseProject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);

    Optional<Vote> findByPostAndUser(Post post, User user);


}
