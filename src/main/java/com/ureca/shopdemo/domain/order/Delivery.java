package com.ureca.shopdemo.domain.order;

import com.ureca.shopdemo.domain.claim.Claim;
import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")      // delivery → shipment
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseTimeEntity {  // BaseTimeEntity 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // OneToOne → ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;                // nullable (클레임 배송일 수도 있음)

    @ManyToOne(fetch = FetchType.LAZY)  // 추가 (반품/교환 시 클레임 연결)
    @JoinColumn(name = "claim_id")
    private Claim claim;

    private String carrier;             // 추가 (택배사)

    private String trackingNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;
}
