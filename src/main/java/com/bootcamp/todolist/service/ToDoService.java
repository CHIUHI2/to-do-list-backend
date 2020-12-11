package com.bootcamp.todolist.service;

import com.bootcamp.todolist.entity.ToDo;
import com.bootcamp.todolist.exception.ToDoDuplicatedException;
import com.bootcamp.todolist.exception.ToDoNotFoundException;
import com.bootcamp.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    public List<ToDo> findAll() {
        return this.toDoRepository.findAll();
    }

    public ToDo add(ToDo toDo) throws ToDoDuplicatedException {
        if(this.toDoRepository.existsByMessage(toDo.getMessage())) {
            throw new ToDoDuplicatedException();
        }

        return this.toDoRepository.insert(toDo);
    }

    public ToDo replace(String id, ToDo toDo) throws ToDoNotFoundException {
        if(!this.toDoRepository.existsById(id)) {
            throw new ToDoNotFoundException();
        }

        toDo.setId(id);

        return this.toDoRepository.save(toDo);
    }

    public void delete(String id) throws ToDoNotFoundException {
        if(!this.toDoRepository.existsById(id)) {
            throw new ToDoNotFoundException();
        }

        this.toDoRepository.deleteById(id);
    }

    public void removeTagFromRespectiveToDos(String tagId) {
        List<ToDo> toDos = this.toDoRepository.findByTags(tagId);

        toDos.forEach(toDo -> {
            Set<String> tags = toDo.getTags();
            tags.remove(tagId);
            toDo.setTags(tags);
            this.toDoRepository.save(toDo);
        });
    }
}
