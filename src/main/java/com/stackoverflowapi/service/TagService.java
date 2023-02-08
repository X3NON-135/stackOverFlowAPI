package com.stackoverflowapi.service;

import com.stackoverflowapi.model.Tag;
import com.stackoverflowapi.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.PageRequest;

public interface TagService {
    List<Tag> getAllTagsByUsers(String ids, PageRequest pageRequest);

    List<User> appendTagsToUsers(Set<Tag> tags, List<User> users);
}
