package com.example.BaseProject.service;

import com.example.BaseProject.dto.SubredditDto;
import com.example.BaseProject.dto.SubredditResponse;
import com.example.BaseProject.exceptions.SpringRedditException;
import com.example.BaseProject.model.Subreddit;
import com.example.BaseProject.repositories.SubredditRepository;
import com.example.BaseProject.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditServiceImpl implements SubredditService{

    private final SubredditRepository repository;
    private final UserDetailsService userDetailsService;


    @Transactional
    @Override
    public SubredditDto save(SubredditDto dto) {
        Subreddit subreddit = maptoSubreddit(dto);
        subreddit.setCreatedDate(new Date().toInstant());
        subreddit.setUser((User) userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        subreddit = repository.save(subreddit);
        return mapToDto(subreddit);
    }

    @Transactional
    @Override
    public SubredditDto update(long id, SubredditDto dto) {
        Subreddit subreddit = repository.findById(dto.getId()).orElseThrow(()->new SpringRedditException("Subreddit could not be found"));
        subreddit.setName(maptoSubreddit(dto).getName());
        subreddit.setDescription(maptoSubreddit(dto).getDescription());

        Subreddit subredditUpdated = repository.save(subreddit);
        return mapToDto(subredditUpdated);

    }

    @Override
    public void delete(long id) {
        repository.findById(id).orElseThrow(()->new SpringRedditException("Subreddit id " + id + " Could not be found"));
        repository.deleteById(id);
    }

    @Override
    public List<SubredditDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubredditResponse findAllPaged(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public SubredditDto findOne(long id) {
        Subreddit subreddit = repository.findById(id).orElseThrow(()->new SpringRedditException("Subreddit id " + id + " could not be found"));
        return mapToDto(subreddit);
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
       return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPost(subreddit.getPosts().size())
                .build();

    }

    private Subreddit maptoSubreddit(SubredditDto subredditDto) {
        return Subreddit.builder()
                .id(subredditDto.getId())
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();

    }
}
