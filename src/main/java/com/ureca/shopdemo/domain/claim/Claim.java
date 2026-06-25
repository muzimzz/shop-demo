package com.ureca.shopdemo.domain.claim;

import com.ureca.shopdemo.domain.admin.Admin;
import com.ureca.shopdemo.domain.order.Order;
import com.ureca.shopdemo.domain.refund.RefundReasonType;
import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "claim")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Claim extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private Admin admin;                // 처리한 관리자 (nullable - 처리 전엔 없음)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimType type;             // 취소/반품/교환

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status;

    private String reason;              // 상세 사유 (nullable)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundReasonType reasonType; // 단순변심/하자 등

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime processedAt;
}
