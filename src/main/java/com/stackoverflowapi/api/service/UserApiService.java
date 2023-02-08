package com.stackoverflowapi.api.service;

import com.stackoverflowapi.dto.UserResponseDto;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UserApiService {
    @GET("users")
    Call<UserResponseDto> getAllUsers(@Query("page") int page,
                                      @Query("pagesize") int count,
                                      @QueryMap() Map<String, String> params);
}
