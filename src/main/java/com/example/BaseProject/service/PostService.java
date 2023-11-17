package com.example.BaseProject.service;

import com.example.BaseProject.dto.PostDto;

import java.util.List;

public interface PostService {

    public PostDto save(PostDto postDto);

    public PostDto update(long id, PostDto postDto);

    public void delete(long id);

    public List<PostDto> getAll();

    public PostDto getOne(long id);

    public List<PostDto> getPostsBySubreddit(long subredditId);

    public List<PostDto> getPostsByUsername(String username);
}
