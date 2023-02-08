package com.stackoverflowapi.service;

import com.stackoverflowapi.model.Question;
import com.stackoverflowapi.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.PageRequest;

public interface QuestionService {
    List<Question> getAllQuestionsByUsers(String ids, PageRequest pageRequest);

    List<User> appendQuestionsToUsers(Set<Question> questions, List<User> users);
}
