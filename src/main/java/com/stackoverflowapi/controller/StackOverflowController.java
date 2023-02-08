package com.stackoverflowapi.controller;

import com.stackoverflowapi.model.Answer;
import com.stackoverflowapi.model.Question;
import com.stackoverflowapi.model.Tag;
import com.stackoverflowapi.model.User;
import com.stackoverflowapi.service.AnswerService;
import com.stackoverflowapi.service.QuestionService;
import com.stackoverflowapi.service.TagService;
import com.stackoverflowapi.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StackOverflowController {
    private final UserService userService;
    private final AnswerService answerService;
    private final TagService tagService;
    private final QuestionService questionService;

    public StackOverflowController(UserService userService,
                                   AnswerService answerService,
                                   TagService tagService,
                                   QuestionService questionService) {
        this.userService = userService;
        this.answerService = answerService;
        this.tagService = tagService;
        this.questionService = questionService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "20") Integer count) {

        List<User> fetchedUsers = userService.getAllUsers(PageRequest.of(page, count));
        List<String> usersIds = userService.getUsersIds(fetchedUsers);
        Set<Answer> userAnswers = new HashSet<>();
        Set<Question> userQuestions = new HashSet<>();
        Set<Tag> userTags = new HashSet<>();
        for (String id : usersIds) {
            userAnswers.addAll(answerService.getAllAnswersByUsers(id,
                    PageRequest.of(page, count)));
            userQuestions.addAll(questionService.getAllQuestionsByUsers(id,
                    PageRequest.of(page, count)));
            userTags.addAll(tagService.getAllTagsByUsers(id,
                    PageRequest.of(page, count)));
        }
        fetchedUsers = answerService.appendAnswersToUsers(userAnswers, fetchedUsers);
        fetchedUsers = questionService.appendQuestionsToUsers(userQuestions, fetchedUsers);
        fetchedUsers = tagService.appendTagsToUsers(userTags, fetchedUsers);
        fetchedUsers.forEach(System.out::println);
        return fetchedUsers;
    }

    @GetMapping("/users/{ids}/answers")
    public List<Answer> getAllAnswersByUsers(@PathVariable String ids,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "20") Integer count) {
        return answerService.getAllAnswersByUsers(ids, PageRequest.of(page, count));
    }

    @GetMapping("/users/{ids}/tags")
    public List<Tag> getAllTagsByUsers(@PathVariable String ids,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "20") Integer count) {
        return tagService.getAllTagsByUsers(ids, PageRequest.of(page, count));
    }

    @GetMapping("/users/{ids}/questions")
    public List<Question> getAllQuestionsByUsers(@PathVariable String ids,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "20") Integer count) {
        return questionService.getAllQuestionsByUsers(ids,
                PageRequest.of(page, count));
    }
}
