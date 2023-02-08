package com.stackoverflowapi.api.service;

import com.stackoverflowapi.dto.TagResponseDto;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TagApiService {
    @GET("users/{ids}/tags")
    Call<TagResponseDto> getTagsByUsers(@Path("ids") String ids,
                                        @Query("page") int page,
                                        @Query("pagesize") int count,
                                        @QueryMap() Map<String, String> params);
}
