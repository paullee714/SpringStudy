package org.wool.springsecurityotp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;

    @Column
    private String optCode;

    @Column
    private boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public Map<String, Object> responseOtp() {
        return Map.of(
                "otpId", otpId,
                "optCode", optCode,
                "isChecked", isChecked,
                "member", member.memberResponse()
        );
    }
}
