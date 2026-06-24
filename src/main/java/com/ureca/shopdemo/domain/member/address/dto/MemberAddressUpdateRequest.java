package com.ureca.shopdemo.domain.member.address.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAddressUpdateRequest {

    private String recipient;

    private String phone;

    private String zipCode;

    private String roadAddress;

    private String detailAddress;
}
