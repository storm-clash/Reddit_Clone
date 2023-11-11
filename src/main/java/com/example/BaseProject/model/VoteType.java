package com.example.BaseProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private final int direction;

    VoteType(int direction) {
        this.direction = direction;
    }

}
