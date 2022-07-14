package com.example.myblog.member.repository;

import com.example.myblog.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //엔티티랑 엔티티의 id 클래스 v필요.
    //"Select member from Member shere nickname = ?"
    //@Query() 자동입력해준다고 ? 난잘안돼
    Member findByNickname(String nickname);//jpa가 알아서 가져올거 찾아줘.. nickname을 찾을거니 파라미터로 nickname을넣어줘야해


    Optional<Member> findByUsername(String username); // username을 token으로 가져오면, member랑 또 뭐랑 같은지 비교해줘
}
