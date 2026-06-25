package com.ureca.shopdemo.domain.member.dto;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.address.MemberAddress;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MemberDetailResponse {

    private String name;

    private String email;

    private String phone;

    private boolean isBlocked;

    private LocalDateTime blockedAt;

    List<MemberAddressResponse> addressList;

    public static MemberDetailResponse toDto(Member member, List<MemberAddress> memberAddressList) {
        return MemberDetailResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .isBlocked(member.isBlocked())
                .blockedAt(member.getBlockedAt())
                .addressList(memberAddressList.stream()
                        .map(MemberAddressResponse::toDto)
                        .toList())
                .build();
    }
}
