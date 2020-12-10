package com.bootcamp.todolist.controller;

import com.bootcamp.todolist.dto.TagRequest;
import com.bootcamp.todolist.dto.TagResponse;
import com.bootcamp.todolist.exception.TagNotFoundException;
import com.bootcamp.todolist.mapper.TagMapper;
import com.bootcamp.todolist.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private TagMapper tagMapper;

    @GetMapping
    public List<TagResponse> findAll() {
        return this.tagMapper.toResponse(this.tagService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponse add(@RequestBody TagRequest tagRequest) {
        return this.tagMapper.toResponse(this.tagService.add(this.tagMapper.toEntity(tagRequest)));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws TagNotFoundException {
        this.tagService.delete(id);
    }
}
