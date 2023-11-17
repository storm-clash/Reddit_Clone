package com.example.BaseProject.service;

import com.example.BaseProject.dto.CommentDto;

import java.util.List;

public interface CommentService {

    public void save(CommentDto commentDto);

    public CommentDto update(long id, CommentDto commentDto);

    public void delete(long id);

    public CommentDto getOne(long id);

    public List<CommentDto> getAllCommentsForPost(long id);

    public List<CommentDto> getAllCommentForUser(long id);

}
