package com.example.BaseProject.service;

import com.example.BaseProject.dto.VoteDto;
import com.example.BaseProject.exceptions.PostNotFoundException;
import com.example.BaseProject.exceptions.SpringRedditException;
import com.example.BaseProject.model.Post;
import com.example.BaseProject.model.Vote;
import com.example.BaseProject.repositories.PostRepository;
import com.example.BaseProject.repositories.VoteRepository;
import com.example.BaseProject.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.BaseProject.model.VoteType.UPVOTE;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService{

    private final VoteRepository repository;
    private final PostRepository postRepository;
    private final UserDetailsService userDetailsService;
    @Override
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post " + voteDto.getPostId() + " Could not be found"));
        User user = (User) userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Vote> voteByPostAndUser = repository.findByPostAndUser(post, user);
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        repository.save(mapToVote(voteDto));
        postRepository.save(post);
    }
    private Vote mapToVote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(()->new PostNotFoundException("Post ID- " + voteDto.getPostId() + " Could not be found"));
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .build();

    }

}
