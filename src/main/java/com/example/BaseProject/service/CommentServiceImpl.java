package com.example.BaseProject.service;

import com.example.BaseProject.dto.CommentDto;
import com.example.BaseProject.exceptions.PostNotFoundException;
import com.example.BaseProject.exceptions.UserNotFoundException;
import com.example.BaseProject.exceptions.UsernameNotFoundException;
import com.example.BaseProject.model.Comment;
import com.example.BaseProject.model.NotificationEmail;
import com.example.BaseProject.model.Post;
import com.example.BaseProject.repositories.CommentRepository;
import com.example.BaseProject.repositories.PostRepository;
import com.example.BaseProject.repositories.UserRepository;
import com.example.BaseProject.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository repository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    @Override
    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(()->new PostNotFoundException("Post id " + commentDto.getPostId() + " Could not find"));
        Comment comment = mapToComment(commentDto);
        User user = (User) userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setCreatedDate(Instant.now());
        comment.setPost(post);
        comment.setUser(user);
        comment = repository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + post.getUrl());
                sendCommentNotification(message, post.getUser());


    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    @Override
    public CommentDto update(long id, CommentDto commentDto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public CommentDto getOne(long id) {
        return null;
    }

    @Override
    public List<CommentDto> getAllCommentsForPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException("Post id " +id + " Could not be found"));
        return repository.findByPost(post)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentForUser(long id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User id " +id+" Could not be found"));
        return repository.findByUser(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getPostId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .username(comment.getUser().getUsername())
                .build();
    }
    private Comment mapToComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(()->new PostNotFoundException("Could not find a post with id "+ commentDto.getPostId()));
               return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .post(post)
                .build();
    }
}
