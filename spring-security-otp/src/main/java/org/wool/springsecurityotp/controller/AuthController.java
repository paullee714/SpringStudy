package org.wool.springsecurityotp.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wool.springsecurityotp.model.Member;
import org.wool.springsecurityotp.model.Otp;
import org.wool.springsecurityotp.service.MemberService;

@RestController
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestBody Member member) {

        Member user = memberService.addMember(member);

        return new ResponseEntity(
                user.memberResponse(),
                HttpStatus.CREATED
        );

    }

    @PostMapping("/user/auth")
    public ResponseEntity auth(@RequestBody Member member) {
        Otp otp = memberService.auth(member);

        return new ResponseEntity(
                otp.responseOtp(),
                HttpStatus.CREATED
        );

    }

    @PostMapping("/otp/check")
    public ResponseEntity checkOtp(@RequestBody MemberOtpCode memberOtpCode, HttpServletResponse response) {
        Otp otp = new Otp();
        otp.setOptCode(memberOtpCode.otpCode());
        memberService.findMemberByMemberName(memberOtpCode.memberName()).ifPresent(otp::setMember);

        Otp result = memberService.checkOtp(otp);

        if (result!=null) {
            return new ResponseEntity(
                    result.responseOtp(),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    "Fail",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}
