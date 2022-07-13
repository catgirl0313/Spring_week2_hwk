package com.example.myblog.repository;

import com.example.myblog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //엔티티랑 엔티티의 id 클래스 v필요.

    //"Select member from Member shere nickname = ?"
    Member findByNickname(String nickname);//jpa가 알아서 가져올거 찾아줘.. nickname을 찾을거니 파라미터로 nickname을넣어줘야해
}
