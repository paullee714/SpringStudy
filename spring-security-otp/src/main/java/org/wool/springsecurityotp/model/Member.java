package org.wool.springsecurityotp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"memberName"}))
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String memberName;

    @Column
    private String memberPassword;

    @OneToMany(mappedBy = "member")
    private List<Otp> otpLists = new ArrayList<>();

    public Map<String, Object> memberResponse() {
        return Map.of(
                "memberId", memberId,
                "memberName", memberName
        );
    }
}
