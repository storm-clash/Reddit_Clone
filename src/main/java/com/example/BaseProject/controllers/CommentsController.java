package com.example.BaseProject.controllers;

import com.example.BaseProject.dto.CommentDto;
import com.example.BaseProject.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/comments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentServiceImpl service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CommentDto commentDto) {
        service.save(commentDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("by-post/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentForPost(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCommentsForPost(id));
    }
    @GetMapping("by-user/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentForUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCommentForUser(id));
    }
}
