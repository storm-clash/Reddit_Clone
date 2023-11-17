package com.example.BaseProject.service;

import com.example.BaseProject.auth.AuthService;
import com.example.BaseProject.dto.PostDto;
import com.example.BaseProject.exceptions.SpringRedditException;
import com.example.BaseProject.exceptions.SubredditNotFoundException;
import com.example.BaseProject.exceptions.UsernameNotFoundException;
import com.example.BaseProject.model.Post;
import com.example.BaseProject.model.Subreddit;
import com.example.BaseProject.repositories.PostRepository;
import com.example.BaseProject.repositories.SubredditRepository;
import com.example.BaseProject.repositories.UserRepository;
import com.example.BaseProject.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository repository;
    private final UserDetailsService userDetailsService;
    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public PostDto save( PostDto postDto) {
        Post post = maptoPost(postDto);
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName()).orElseThrow(()->new SpringRedditException("Could not find the subreddit"));
        post.setCreatedDate(Instant.now());
        post.setSubreddit(subreddit);
        post.setUser((User) userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        post = repository.save(post);
        return maptoDto(post);
    }

    @Transactional
    @Override
    public PostDto update(long id, PostDto postDto) {
        return null;
    }

    @Override
    public void delete(long id) {
        repository.findById(id).orElseThrow(()->new SpringRedditException("Post id " +id+ " could not be found"));
        repository.deleteById(id);
    }

    @Override
    public List<PostDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::maptoDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getOne(long id) {
        Post post = repository.findById(id).orElseThrow(()->new SpringRedditException("Post id " +id+ " could not be found"));
        return maptoDto(post);
    }

    @Override
    public List<PostDto> getPostsBySubreddit(long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(()->new SubredditNotFoundException("Subreddit Could not be found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(this::maptoDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Could not found user with that username" + username));
        return postRepository.findByUser(user).stream().map(this::maptoDto).collect(Collectors.toList());
    }

    private PostDto maptoDto(Post post) {
        Duration diference = Duration.between(post.getCreatedDate(), Instant.now());

        long seconds = diference.getSeconds();


        return PostDto.builder()

                .id(post.getPostId())
                .subredditName(post.getSubreddit().getName())
                .postName(post.getPostName())
                .url(post.getUrl())
                .description(post.getDescription())

                .username(userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUsername())
                .voteCount(post.getVoteCount())
                .duration(Duration.ofDays(seconds * 1000))
                .build();
    }

    private Post maptoPost(PostDto dto) {
        Subreddit subreddit = Subreddit.builder()
                .name(dto.getSubredditName())
                .build();
        return Post.builder()
                .postId(dto.getId())
                .subreddit(subreddit)
                .PostName(dto.getPostName())
                .url(dto.getUrl())
                .description(dto.getDescription())
                .build();
    }
}
