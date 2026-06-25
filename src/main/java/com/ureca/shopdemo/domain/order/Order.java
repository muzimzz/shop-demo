package com.ureca.shopdemo.domain.order;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {     // BaseTimeEntity 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, unique = true)
    private String orderNo;             // 추가 (주문번호)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private int productAmount;          // 추가 (상품금액)

    @Column(nullable = false)
    private int discountAmount;         // 추가 (할인금액)

    @Column(nullable = false)
    private int shippingFee;            // 추가 (배송비)

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false)
    private String shipRecipient;       // 추가 (수령인)

    @Column(nullable = false)
    private String shipPhone;           // 추가 (연락처)

    @Column(nullable = false)
    private String shipZipCode;         // 추가 (우편번호)

    @Column(name = "ship_address", nullable = false)
    private String roadAddress;         // 컬럼명 ship_address로 매핑

    @Column(name = "ship_detail_address")
    private String detailAddress;       // 컬럼명 ship_detail_address로 매핑

    private String shipMessage;         // 추가 (배송메시지)

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    private LocalDateTime canceledAt;
}
