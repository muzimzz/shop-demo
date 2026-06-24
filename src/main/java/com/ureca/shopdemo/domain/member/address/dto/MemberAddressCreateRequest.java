package com.ureca.shopdemo.domain.member.address.dto;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.address.MemberAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAddressCreateRequest {

    private String recipient;

    private String phone;

    private String zipCode;

    private String roadAddress;

    private String detailAddress;

    private boolean isDefault;

    public MemberAddress toEntity(Member member, boolean isDefault) {
        return MemberAddress.builder()
                .member(member)
                .recipient(this.recipient)
                .phone(this.phone)
                .zipCode(this.zipCode)
                .roadAddress(this.roadAddress)
                .detailAddress(this.detailAddress)
                .isDefault(isDefault)
                .build();
    }
}
