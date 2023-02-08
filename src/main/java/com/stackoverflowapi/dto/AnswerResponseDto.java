package com.stackoverflowapi.dto;

import com.google.gson.annotations.SerializedName;
import com.stackoverflowapi.model.Answer;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnswerResponseDto {
    @SerializedName("items")
    private List<Answer> answers = new ArrayList<>();
}
