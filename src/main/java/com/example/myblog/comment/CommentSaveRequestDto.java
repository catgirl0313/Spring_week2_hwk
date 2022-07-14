package com.example.myblog.comment;

import com.example.myblog.article.Articles;
import com.example.myblog.member.domain.Member;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentSaveRequestDto {

    @NotNull
    private String content;

    private String username;  //string 아니고 Member 를 넘겨준다
    @NotNull
    private Long articlesId;

    //Dto를 entity로
//    public Comments toEntity() {
//        return new Comments(content, member, articles);
//    }
}
