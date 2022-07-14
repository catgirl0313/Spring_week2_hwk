package com.example.myblog.comment;

import com.example.myblog.article.Articles;
import com.example.myblog.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    //writer -> 회원이랑 직접 연관string 이 아니라.
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne // 댓글 - > 게시글
    @JoinColumn(name = "articles_id")
    private Articles articles;


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

    public Comments() {
    }

    private Comments(String content, Member member, Articles articles) {
        this.content = content;
        this.member = member;
        this.articles = articles;
    }

    public static Comments createComments(String content, Member member, Articles articles){
        return new Comments(content, member, articles);
    }
}
