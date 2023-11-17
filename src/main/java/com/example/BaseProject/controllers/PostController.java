package com.example.BaseProject.controllers;

import com.example.BaseProject.dto.PostDto;
import com.example.BaseProject.model.Post;
import com.example.BaseProject.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl service;

    @PostMapping
    public ResponseEntity<PostDto> save(@RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save( postDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOne(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostDto>> getPostsBySubreddit(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPostsBySubreddit(id));
    }

    @GetMapping("by-user")
    public ResponseEntity<List<PostDto>> getPostsByUsername(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPostsByUsername(username));
    }


}
