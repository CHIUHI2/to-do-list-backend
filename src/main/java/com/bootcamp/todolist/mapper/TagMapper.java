package com.bootcamp.todolist.mapper;

import com.bootcamp.todolist.dto.TagRequest;
import com.bootcamp.todolist.dto.TagResponse;
import com.bootcamp.todolist.entity.Tag;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TagMapper {
    public Tag toEntity(TagRequest tagRequest) {
        Tag tag = new Tag();

        BeanUtils.copyProperties(tagRequest, tag);

        return tag;
    }

    public TagResponse toResponse(Tag tag) {
        TagResponse tagResponse = new TagResponse();

        BeanUtils.copyProperties(tag, tagResponse);

        return tagResponse;
    }

    public List<TagResponse> toResponse(List<Tag> tags) {
        return tags.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
