package com.ureca.shopdemo.domain.member.address.dto;

import com.ureca.shopdemo.domain.member.address.MemberAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberAddressResponse {

    private Long id;

    private String recipient;

    private String phone;

    private String zipCode;

    private String roadAddress;

    private String detailAddress;

    private boolean isDefault;

    public static MemberAddressResponse toDto(MemberAddress memberAddress) {
        return MemberAddressResponse.builder()
                .id(memberAddress.getId())
                .recipient(memberAddress.getRecipient())
                .phone(memberAddress.getPhone())
                .zipCode(memberAddress.getZipCode())
                .roadAddress(memberAddress.getRoadAddress())
                .detailAddress(memberAddress.getDetailAddress())
                .isDefault(memberAddress.isDefault())
                .build();
    }
}
