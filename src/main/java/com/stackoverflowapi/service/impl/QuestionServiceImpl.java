package com.stackoverflowapi.service.impl;

import com.stackoverflowapi.api.ApiResponseDto;
import com.stackoverflowapi.api.filter.QuestionFilter;
import com.stackoverflowapi.api.service.QuestionApiService;
import com.stackoverflowapi.dto.QuestionResponseDto;
import com.stackoverflowapi.model.Question;
import com.stackoverflowapi.model.User;
import com.stackoverflowapi.service.QuestionService;
import com.stackoverflowapi.util.RetrofitUtil;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionApiService apiService;
    private final QuestionFilter questionFilter;
    private final Retrofit retrofit;

    public QuestionServiceImpl() {
        questionFilter = new QuestionFilter();
        retrofit = RetrofitUtil.getRetrofitInstance();
        apiService = retrofit.create(QuestionApiService.class);
    }

    @Override
    public List<Question> getAllQuestionsByUsers(String ids, PageRequest pageRequest) {
        Call<QuestionResponseDto> questionsCall = apiService.getQuestionsByUsers(ids,
                pageRequest.getPageNumber(), pageRequest.getPageSize(),
                questionFilter.getUrlFilters());
        ApiResponseDto<List<Question>> apiResponse = new ApiResponseDto<>();
        List<Question> questions = apiResponse.getContent();
        try {
            Response<QuestionResponseDto> response = questionsCall.execute();
            if (response.isSuccessful() && response.body() != null) {
                questions = Objects.requireNonNull(response.body()).getQuestions();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error occurs creating the request "
                    + "or decoding the response", e);
        }
        return questions;
    }

    @Override
    public List<User> appendQuestionsToUsers(Set<Question> questions, List<User> users) {
        Map<Integer, Long> userQuestions = questions.stream()
                .collect(Collectors.groupingBy(a -> a.getOwner().getUserId(),
                Collectors.counting()));
        for (User user : users) {
            user.setQuestionCount(userQuestions.get(user.getUserId()));
        }
        return users;
    }
}
