package com.ureca.shopdemo.domain.member.address;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.address.dto.MemberAddressUpdateRequest;
import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")   // member_address → address
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberAddress extends BaseTimeEntity {    // BaseTimeEntity 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String roadAddress;

    private String detailAddress;

    @Column(nullable = false)
    private boolean isDefault = false;

    public void updateAddress(MemberAddressUpdateRequest request) {
        this.recipient = request.getRecipient();
        this.phone = request.getPhone();
        this.zipCode = request.getZipCode();
        this.roadAddress = request.getRoadAddress();
        this.detailAddress = request.getDetailAddress();
    }

    public void toggleDefault() {
        this.isDefault = !this.isDefault;
    }
}
