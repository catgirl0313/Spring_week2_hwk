package com.example.myblog.comment;

import com.example.myblog.config.security.MemberDetailsImpl;
import com.example.myblog.config.security.jwt.JwtDecoder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;

    }


    //댓글 목록보기
    @GetMapping("/api/comment/{articlesId}") //게시글마다 댓글 다르니까~!
    public List<CommentResponseDto> showComments(@PathVariable Long articlesId) {
        return commentService.showComments(articlesId);

    }
    //댓글 작성
    @PostMapping("/api/comment")
    public void writeComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestBody CommentSaveRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        memberDetails.getMember().getUsername();
        commentService.writeComment(dto);

    }
    //댓글 수정
    @PutMapping("/api/comment")
    public void updateComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestBody CommentUpdateRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
//
//        String token = servletRequest.getHeader(HttpHeaders.AUTHORIZATION); //토큰이야.
//        //useDtails 에 안에꺼를들고와

        commentService.updateComment(dto);

    }
    //댓글 삭제
    @DeleteMapping("/api/comment")
    public void deleteComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestBody CommentDeleteRequestDto dto){
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.deleteComment(dto);;

    }
}
