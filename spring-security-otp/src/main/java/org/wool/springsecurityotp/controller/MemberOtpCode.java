package org.wool.springsecurityotp.controller;

public record MemberOtpCode(
        String memberName,
        String otpCode
) {
}
