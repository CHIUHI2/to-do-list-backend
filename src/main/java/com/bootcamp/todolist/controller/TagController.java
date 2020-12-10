package com.bootcamp.todolist.controller;

import com.bootcamp.todolist.dto.TagResponse;
import com.bootcamp.todolist.mapper.TagMapper;
import com.bootcamp.todolist.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<TagResponse> getAll() {
        return this.tagMapper.toResponse(this.tagService.findAll());
    }
}
