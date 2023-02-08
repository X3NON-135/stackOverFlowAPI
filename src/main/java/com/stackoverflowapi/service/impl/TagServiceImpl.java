package com.stackoverflowapi.service.impl;

import com.stackoverflowapi.api.ApiResponseDto;
import com.stackoverflowapi.api.filter.TagFilter;
import com.stackoverflowapi.api.service.TagApiService;
import com.stackoverflowapi.dto.TagResponseDto;
import com.stackoverflowapi.model.Tag;
import com.stackoverflowapi.model.User;
import com.stackoverflowapi.service.TagService;
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
public class TagServiceImpl implements TagService {
    private final TagApiService apiService;
    private final TagFilter tagFilter;
    private final Retrofit retrofit;

    public TagServiceImpl() {
        tagFilter = new TagFilter();
        retrofit = RetrofitUtil.getRetrofitInstance();
        apiService = retrofit.create(TagApiService.class);
    }

    @Override
    public List<Tag> getAllTagsByUsers(String ids, PageRequest pageRequest) {
        Call<TagResponseDto> tagsCall = apiService.getTagsByUsers(ids, pageRequest.getPageNumber(),
                pageRequest.getPageSize(), tagFilter.getUrlFilters());
        ApiResponseDto<List<Tag>> apiResponse = new ApiResponseDto<>();
        List<Tag> tags = apiResponse.getContent();
        try {
            Response<TagResponseDto> response = tagsCall.execute();
            if (response.isSuccessful() && response.body() != null) {
                tags = Objects.requireNonNull(response.body()).getTags().stream()
                        .filter(t -> t.getName() != null)
                        .filter(t -> t.getName().equals("java")
                                || t.getName().equals(".net")
                                || t.getName().equals("docker")
                                || t.getName().equals("C#"))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error occurs creating the request "
                    + "or decoding the response", e);
        }
        return tags;
    }

    @Override
    public List<User> appendTagsToUsers(Set<Tag> tags, List<User> users) {
        Map<Integer, List<Tag>> userTags = tags.stream()
                .collect(Collectors.groupingBy(Tag::getUserId));

        for (Map.Entry<Integer, List<Tag>> entry : userTags.entrySet()) {
            for (User user : users) {
                if (user.getUserId().equals(entry.getKey())) {
                    String tagName = entry.getValue().stream().map(Tag::getName)
                            .collect(Collectors.joining(";"));
                    user.setTags(tagName);
                }
            }
        }
        return users;
    }
}
