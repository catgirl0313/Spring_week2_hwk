package com.example.myblog.comment;

import com.example.myblog.member.domain.Member;
import lombok.Data;

@Data
public class CommentUpdateRequestDto {
    private Long id;
    private String content;
    private String username;

}
