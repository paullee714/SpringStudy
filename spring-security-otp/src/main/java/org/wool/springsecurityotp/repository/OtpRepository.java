package org.wool.springsecurityotp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wool.springsecurityotp.model.Otp;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findOtpByMember_MemberName(String memberName);
}
