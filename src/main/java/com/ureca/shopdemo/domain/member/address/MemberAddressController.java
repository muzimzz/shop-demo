package com.ureca.shopdemo.domain.member.address;

import com.ureca.shopdemo.domain.member.address.dto.MemberAddressCreateRequest;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressResponse;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressUpdateRequest;
import com.ureca.shopdemo.domain.member.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/address")
public class MemberAddressController {

    private final MemberAddressService memberAddressService;

    // 배송지 목록 조회
    @GetMapping
    public ResponseEntity<List<MemberAddressResponse>> findAll(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(
                memberAddressService.findByMemberId(userDetails.getMember().getId()));
    }

    // 배송지 추가
    @PostMapping
    public ResponseEntity<MemberAddressResponse> addAddress(
            @RequestBody MemberAddressCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberAddressService.addAddress(request, userDetails.getMember().getId()));
    }

    // 배송지 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateAddress(
            @PathVariable Long id,
            @RequestBody MemberAddressUpdateRequest request) {
        memberAddressService.updateAddress(id, request);
        return ResponseEntity.ok().build();
    }

    // 기본 배송지 변경
    @PatchMapping("/{id}/default")
    public ResponseEntity<Void> updateDefault(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberAddressService.updateDefaultAddress(id, userDetails.getMember().getId());
        return ResponseEntity.ok().build();
    }

    // 배송지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberAddressService.deleteAddress(id, userDetails.getMember().getId());
        return ResponseEntity.noContent().build();
    }
}
