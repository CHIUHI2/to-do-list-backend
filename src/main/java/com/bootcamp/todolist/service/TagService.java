package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.Tag;
import com.bootcamp.todolist.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAll() {
        return this.tagRepository.findAll();
    }

    public Tag add(Tag tag) {
        return this.tagRepository.insert(tag);
    }
}
