package org.wool.springsecurityotp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wool.springsecurityotp.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByMemberName(String memberName);

}
