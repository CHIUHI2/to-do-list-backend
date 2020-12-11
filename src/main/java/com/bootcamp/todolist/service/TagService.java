package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.Tag;
import com.bootcamp.todolist.exception.TagDuplicatedException;
import com.bootcamp.todolist.exception.TagNotFoundException;
import com.bootcamp.todolist.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ToDoService toDoService;

    public List<Tag> findAll() {
        return this.tagRepository.findAll();
    }

    public Tag add(Tag tag) throws TagDuplicatedException {
        if(this.tagRepository.existsByMessage(tag.getMessage().trim())) {
            throw new TagDuplicatedException();
        }

        return this.tagRepository.insert(tag);
    }

    public void delete(String id) throws TagNotFoundException {
        if(!this.tagRepository.existsById(id)) {
            throw new TagNotFoundException();
        }

        this.toDoService.removeTagFromRespectiveToDos(id);

        this.tagRepository.deleteById(id);
    }
}
