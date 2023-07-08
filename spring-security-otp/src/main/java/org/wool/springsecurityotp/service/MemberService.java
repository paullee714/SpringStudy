package org.wool.springsecurityotp.service;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.wool.springsecurityotp.model.Member;
import org.wool.springsecurityotp.model.Otp;
import org.wool.springsecurityotp.repository.MemberRepository;
import org.wool.springsecurityotp.repository.OtpRepository;
import org.wool.springsecurityotp.util.GenerateCodeUtil;

import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final OtpRepository otpRepository;

    public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository, OtpRepository otpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.otpRepository = otpRepository;
    }

    public Member addMember(Member member) {
        member.setMemberPassword(passwordEncoder.encode(member.getMemberPassword()));
        return memberRepository.save(member);
    }

    public Otp auth(Member member) {
        Optional<Member> o = memberRepository.findMemberByMemberName(member.getMemberName());

        if (o.isPresent()) {
            Member m = o.get();
            System.out.println(member.getMemberPassword());
            System.out.println(m.getMemberPassword());
            if (passwordEncoder.matches(member.getMemberPassword(), m.getMemberPassword())) {
                return generateOtp(m);
            } else {
                throw new BadCredentialsException("Bad Credentials");
            }
        } else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    public Otp generateOtp(Member m) {
        String code = GenerateCodeUtil.generateCode();
        Otp otp = new Otp(null, code, false, m);
        return otpRepository.save(otp);
    }

    public Otp checkOtp(Otp otpToValidate) {
        Optional<Otp> userOpt = otpRepository.findOtpByMember_MemberName(otpToValidate.getMember().getMemberName());

        if (userOpt.isPresent()) {
            Otp otp = userOpt.get();
            if(otpToValidate.getOptCode().equals(otp.getOptCode())){
                otp.setChecked(true);
                otpRepository.save(otp);
                return otp;
            }
        }
        return null;
    }

    public Optional<Member> findMemberByMemberName(String memberName) {
        return memberRepository.findMemberByMemberName(memberName);
    }

}
