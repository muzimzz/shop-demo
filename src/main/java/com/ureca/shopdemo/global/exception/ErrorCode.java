package com.ureca.shopdemo.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Member
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다.", 404),
    DUPLICATE_EMAIL("이미 사용중인 이메일입니다.", 400),
    DUPLICATE_TELNO("이미 사용중인 전화번호입니다.", 400),
    INVALID_PASSWORD("비밀번호가 올바르지 않습니다.", 400),
    INVALID_PASSWORD_CONFIRM("비밀번호가 일치하지 않습니다.", 400),
    ALREADY_WITHDRAWN("이미 탈퇴한 회원입니다.", 400)
    ;

    private final String message;
    private final int statusCode;
}
