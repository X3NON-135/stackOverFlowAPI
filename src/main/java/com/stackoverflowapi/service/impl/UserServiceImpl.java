package com.stackoverflowapi.service.impl;

import com.stackoverflowapi.api.ApiResponseDto;
import com.stackoverflowapi.api.filter.UserFilter;
import com.stackoverflowapi.api.service.UserApiService;
import com.stackoverflowapi.dto.UserResponseDto;
import com.stackoverflowapi.model.User;
import com.stackoverflowapi.service.UserService;
import com.stackoverflowapi.util.RetrofitUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@Service
public class UserServiceImpl implements UserService {
    private final UserApiService apiService;
    private final UserFilter userFilter;
    private final Retrofit retrofit;

    public UserServiceImpl() {
        userFilter = new UserFilter();
        retrofit = RetrofitUtil.getRetrofitInstance();
        apiService = retrofit.create(UserApiService.class);
    }

    @Override
    public List<User> getAllUsers(PageRequest pageRequest) {
        Call<UserResponseDto> usersCall = apiService.getAllUsers(pageRequest.getPageNumber(),
                pageRequest.getPageSize(), userFilter.getUrlFilters());
        ApiResponseDto<List<User>> apiResponse = new ApiResponseDto<>();
        List<User> users = apiResponse.getContent();
        try {
            Response<UserResponseDto> response = usersCall.execute();
            if (response.isSuccessful() && response.body() != null) {
                users = Objects.requireNonNull(response.body()).getUsers().stream()
                        .filter(u -> u.getLocation() != null)
                        .filter(u -> u.getLocation().contains("France")
                                || u.getLocation().contains("Moldova"))
                        .filter(u -> u.getReputation() >= 223)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error occurs creating the request "
                    + "or decoding the response", e);
        }
        return users;
    }

    @Override
    public List<String> getUsersIds(List<User> users) {
        return (users != null && users.size() > 0) ? users.stream()
                .map(u -> u.getUserId().toString())
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
