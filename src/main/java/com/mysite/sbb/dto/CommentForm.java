package com.mysite.sbb.dto;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message="필수항목입니다.")
    @Size(max=20)
    private String userName;

    @NotEmpty(message="필수항목입니다.")
    @Size(max=10)
    private String password;
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

}
