package com.stackoverflowapi.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("display_name")
    private String name;
    private String location;
    private Long answerCount;
    private Long questionCount;
    @SerializedName("link")
    private String profileLink;
    @SerializedName("profile_image")
    private String avatarLink;
    private int reputation;
    private String tags;

    public User(Tag tag) {
    }

    @Override
    public String toString() {
        return "User {" + '\n'
                + "User name: " + name + '\n'
                + "Location: " + location + '\n'
                + "Answer count: " + answerCount + '\n'
                + "Question count: " + questionCount + '\n'
                + "Tags: " + tags + '\n'
                + "Link to profile: " + profileLink + '\n'
                + "Link to avatar: " + avatarLink + '\n'
                + '}';
    }
}
