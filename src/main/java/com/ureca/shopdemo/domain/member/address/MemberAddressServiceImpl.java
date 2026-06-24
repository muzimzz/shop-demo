package com.ureca.shopdemo.domain.member.address;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.MemberRepository;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressCreateRequest;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressResponse;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberAddressServiceImpl implements MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberAddressResponse> findByMemberId(Long memberId) {
        return memberAddressRepository.findByMemberId(memberId)
                .stream()
                .map(MemberAddressResponse::toDto)
                .toList();
    }

    @Override
    public MemberAddressResponse addAddress(MemberAddressCreateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        boolean isFirstAddress = memberAddressRepository.existsByMemberId(memberId);
        MemberAddress savedAddress = memberAddressRepository.save(request.toEntity(member, !isFirstAddress));
        return MemberAddressResponse.toDto(savedAddress);
    }

    @Override
    public void updateAddress(Long id, MemberAddressUpdateRequest request) {
        MemberAddress address = memberAddressRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        address.updateAddress(request);
    }

    @Override
    public void updateDefaultAddress(Long id, Long memberId) {
//      기본 배송지가 없을 경우 에러 발생
//      MemberAddress defaultAddress = memberAddressRepository.findDefaultAddress()
//              .orElseThrow(IllegalArgumentException::new);
//
//      defaultAddress.toggleDefault();

        memberAddressRepository.findByMemberIdAndIsDefaultTrue(memberId)
                .ifPresent(MemberAddress::toggleDefault);

        MemberAddress address = memberAddressRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        address.toggleDefault();
    }

    @Override
    public void deleteAddress(Long id, Long memberId) {
         MemberAddress address = memberAddressRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

         boolean isDefault = address.isDefault();

        memberAddressRepository.deleteById(id);

        if (isDefault) {
            memberAddressRepository.findTopByMemberIdOrderByIdDesc(memberId)
                    .ifPresent(MemberAddress::toggleDefault);


        }
    }


}
