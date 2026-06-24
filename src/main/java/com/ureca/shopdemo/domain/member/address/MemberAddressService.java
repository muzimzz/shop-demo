package com.ureca.shopdemo.domain.member.address;

import com.ureca.shopdemo.domain.member.address.dto.MemberAddressCreateRequest;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressResponse;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressUpdateRequest;

import java.util.List;

public interface MemberAddressService {

    List<MemberAddressResponse> findByMemberId(Long memberId);

    MemberAddressResponse addAddress(MemberAddressCreateRequest request, Long memberId);

    void updateAddress(Long id, MemberAddressUpdateRequest request);

    void updateDefaultAddress(Long id, Long memberId);

    void deleteAddress(Long id, Long memberId);
}
