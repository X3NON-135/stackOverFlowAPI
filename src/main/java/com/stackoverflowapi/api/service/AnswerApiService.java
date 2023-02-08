package com.stackoverflowapi.api.service;

import com.stackoverflowapi.dto.AnswerResponseDto;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AnswerApiService {
    @GET("users/{ids}/answers")
    Call<AnswerResponseDto> getAnswersByUsers(@Path("ids") String ids,
                                              @Query("page") int page,
                                              @Query("pagesize") int count,
                                              @QueryMap() Map<String, String> params);
}
