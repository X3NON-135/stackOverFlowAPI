package com.stackoverflowapi.service;

import com.stackoverflowapi.model.Answer;
import com.stackoverflowapi.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.PageRequest;

public interface AnswerService {
    List<Answer> getAllAnswersByUsers(String ids, PageRequest pageRequest);

    List<User> appendAnswersToUsers(Set<Answer> answers, List<User> users);
}
