package com.stackoverflowapi.dto;

import com.google.gson.annotations.SerializedName;
import com.stackoverflowapi.model.Question;
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
public class QuestionResponseDto {
    @SerializedName("items")
    private List<Question> questions = new ArrayList<>();
}
