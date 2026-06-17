package com.ureca.shopdemo.domain.member.dto;

import com.ureca.shopdemo.domain.member.MemberAddress;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberAddressResponse {

    private String roadAddress;

    private String detailAddress;

    private boolean isDefault;

    public static MemberAddressResponse toDto(MemberAddress memberAddress) {
        return MemberAddressResponse.builder()
                .roadAddress(memberAddress.getRoadAddress())
                .detailAddress(memberAddress.getDetailAddress())
                .isDefault(memberAddress.isDefault())
                .build();
    }
}
