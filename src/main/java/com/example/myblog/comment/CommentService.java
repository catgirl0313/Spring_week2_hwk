package com.example.myblog.comment;

import com.example.myblog.article.ArticleRepository;
import com.example.myblog.article.Articles;
import com.example.myblog.member.domain.Member;
import com.example.myblog.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;


    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, MemberRepository memberRepository1, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository1;

        this.articleRepository = articleRepository;
    }

    //댓글 목록보기
    public List<CommentResponseDto> showComments(Long articlesId) {
        //모든 게시글의 모든 댓글 불러오기.
//        commentRepository.findAll()

        //특정 게시글의 모든 댓글 불러오기

        return commentRepository.findByArticlesIdOrderByCreatedAtDesc(articlesId) //DB에서 미리 정리에서 가져옴.//마지막에return써주면 끝ㅋ
                .stream()
                .map(CommentResponseDto::new)//commets -> commentsresponsedto
                .collect(Collectors.toList());


    }
    //댓글 작성
    public void writeComment(CommentSaveRequestDto dto) {
        Member member = memberRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        //Comment 에 저장할 Member Id
//        commentRepository.save(dto.toEntity()); //저장하고 entity로 바꿔 저장- 필요없어.

        Articles articles = articleRepository.findById(dto.getArticlesId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 게시글입니다."));

        //Comment 저장 => 컨트롤러에서 댓글 뜰 때 회원, 게시글 등 모든 정보를 가져오기 부담스러워서 서버에게 맡김.
        commentRepository.save(Comments.createComments(dto.getContent(),member,articles));

    }
    //댓글 수정

    public void updateComment(CommentUpdateRequestDto dto) {
        Comments comments = commentRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다."));

        //댓글을 쓴 사람인가 검증 -대표적으로 토큰을 사용하여 검증. jwt
        //간단하게는 멤버값 비교. 원래는 토큰으로~
        if(dto.getUsername().equals(comments.getMember().getUsername())) { //업데이트 요청한 사람의정보.와 같은지 비교.
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        //통과하면 같다는 뜻인까~
        comments.setContent(dto.getContent()); //dto 에 content꺼내서 바꿔줘.
        //저장 안해도 업데이트돼 jpa의 더티체크? 의 캐시확인..
    }
    //댓글 삭제

    public void deleteComment(CommentDeleteRequestDto dto) {
        Comments comments = commentRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다."));

        if(dto.getUsername().equals(comments.getMember().getUsername())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        commentRepository.deleteById(dto.getId());
    }
}
