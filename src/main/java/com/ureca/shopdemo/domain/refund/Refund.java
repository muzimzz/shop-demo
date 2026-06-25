package com.ureca.shopdemo.domain.refund;

import com.ureca.shopdemo.domain.claim.Claim;
import com.ureca.shopdemo.domain.order.Payment;
import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refund")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;            // order → payment

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;                // admin → claim (reason/content는 Claim으로 이동)

    @Column(nullable = false)
    private int amount;                 // refundAmount → amount

    @Column(nullable = false)
    private int shippingDeduction = 0;  // 추가 (배송비 차감액)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status;

    private LocalDateTime refundedAt;   // processedAt → refundedAt
}
