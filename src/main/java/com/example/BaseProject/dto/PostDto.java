package com.example.BaseProject.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Data
@Builder
public class PostDto {

    private long id;
    private String subredditName;
    private String postName;
    private String url;
    private String description;

    private String username;
    private Integer voteCount;
    private String dayOfDuration;


}
