package com.ureca.shopdemo.domain.member.dto;

import com.ureca.shopdemo.domain.member.Member;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberResponse {

    private String name;

    private String password;

    private String email;

    private String telno;

    private LocalDateTime expiredAt;

    private boolean isBlocked = false;

    private LocalDateTime blockedAt;

    public static MemberResponse toDto (Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .telno(member.getTelno())
                .expiredAt(member.getExpiredAt())
                .isBlocked(member.isBlocked())
                .blockedAt(member.getBlockedAt())
                .build();
    }

}
