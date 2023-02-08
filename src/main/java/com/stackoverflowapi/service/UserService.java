package com.stackoverflowapi.service;

import com.stackoverflowapi.model.User;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    List<User> getAllUsers(PageRequest pageRequest);

    List<String> getUsersIds(List<User> users);
}
