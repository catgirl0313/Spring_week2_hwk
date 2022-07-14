package com.example.myblog.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
//Entity는 JPA가 관리할 객체,
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Articles {

    @Id
    //
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String writer;
    private String content;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy; //누가 만들었는지도 알려줘야해 . 왜?

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;

    public Articles(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public Articles() {
    }
}
