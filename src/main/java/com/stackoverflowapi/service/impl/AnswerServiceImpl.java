package com.stackoverflowapi.service.impl;

import com.stackoverflowapi.api.ApiResponseDto;
import com.stackoverflowapi.api.filter.AnswerFilter;
import com.stackoverflowapi.api.service.AnswerApiService;
import com.stackoverflowapi.dto.AnswerResponseDto;
import com.stackoverflowapi.model.Answer;
import com.stackoverflowapi.model.User;
import com.stackoverflowapi.service.AnswerService;
import com.stackoverflowapi.util.RetrofitUtil;
import java.io.IOException;
import java.util.Collections;
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
public class AnswerServiceImpl implements AnswerService {
    private final AnswerApiService apiService;
    private final AnswerFilter answerFilter;
    private final Retrofit retrofit;

    public AnswerServiceImpl() {
        answerFilter = new AnswerFilter();
        retrofit = RetrofitUtil.getRetrofitInstance();
        apiService = retrofit.create(AnswerApiService.class);
    }

    @Override
    public List<Answer> getAllAnswersByUsers(String ids,
                                             PageRequest pageRequest) {
        Call<AnswerResponseDto> answersCall = apiService.getAnswersByUsers(ids,
                pageRequest.getPageNumber(), pageRequest.getPageSize(),
                answerFilter.getUrlFilters());
        ApiResponseDto<List<Answer>> apiResponse = new ApiResponseDto<>();
        List<Answer> answers = apiResponse.getContent();
        try {
            Response<AnswerResponseDto> response = answersCall.execute();
            if (response.isSuccessful() && response.body() != null
                    && Objects.requireNonNull(response.body()).getAnswers().size() > 1) {
                answers = Objects.requireNonNull(response.body()).getAnswers();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error occurs creating the request "
                    + "or decoding the response", e);
        }
        return answers;
    }

    @Override
    public List<User> appendAnswersToUsers(Set<Answer> answers, List<User> users) {
        Map<Integer, Long> userAnswers = answers.stream()
                .collect(Collectors.groupingBy(a -> a.getOwner().getUserId(),
                        Collectors.counting()));
        if (users != null) {
            for (User user : users) {
                user.setAnswerCount(userAnswers.get(user.getUserId()));
            }
            return users;
        }
        return Collections.emptyList();
    }
}
