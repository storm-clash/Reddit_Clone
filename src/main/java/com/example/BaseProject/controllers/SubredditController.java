package com.example.BaseProject.controllers;

import com.example.BaseProject.dto.SubredditDto;
import com.example.BaseProject.service.SubredditServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subreddit")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SubredditController {

    private final SubredditServiceImpl service;

    @PostMapping
    public ResponseEntity<SubredditDto> save(@RequestBody SubredditDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubredditDto> update(@PathVariable("id")long id, @RequestBody SubredditDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>("Subreddit deleted Successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getOne(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findOne(id));
    }
}
