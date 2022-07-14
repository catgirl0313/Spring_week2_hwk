package com.example.myblog.article;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleSaveDto {


    @NotNull
    private String title;
    @NotNull
    private String writer;
    @NotNull
    private String content;

    public Articles toEntity(){
        return new Articles(title, writer, content);

    }
}
