package com.example.BaseProject.model;

import com.example.BaseProject.exceptions.SpringRedditException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;


@Getter
public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private final int direction;

    VoteType(int direction) {
        this.direction = direction;
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.direction == direction)
                .findAny()
                .orElseThrow(()->new SpringRedditException("Vote not found"));
    }

}
