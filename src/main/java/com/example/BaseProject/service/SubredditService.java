package com.example.BaseProject.service;

import com.example.BaseProject.dto.SubredditDto;
import com.example.BaseProject.dto.SubredditResponse;

import java.util.List;

public interface SubredditService {

    public SubredditDto save(SubredditDto dto);

    public SubredditDto update(long id, SubredditDto dto);

    public void delete(long id);

    public List<SubredditDto> findAll();

    public SubredditResponse findAllPaged(int pageNo, int pageSize);

    public SubredditDto findOne(long id);
}
