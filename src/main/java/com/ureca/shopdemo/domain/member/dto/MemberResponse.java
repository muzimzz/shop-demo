package com.ureca.shopdemo.domain.member.dto;

import com.ureca.shopdemo.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private boolean isBlocked;

    private LocalDateTime blockedAt;

    public static MemberResponse toDto(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .isBlocked(member.isBlocked())
                .blockedAt(member.getBlockedAt())
                .build();
    }
}
