package com.example.BaseProject.controllers;

import com.example.BaseProject.dto.VoteDto;
import com.example.BaseProject.service.VoteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/votes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VoteController {

    private final VoteServiceImpl service;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        service.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
