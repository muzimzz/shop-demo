package com.ureca.shopdemo.domain.order;

import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {   // BaseTimeEntity 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType method;         // name → method

    @Column(nullable = false)
    private int amount;                 // paidAmount → amount

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;       // isPaid(boolean) → status(enum)

    private String pgTid;               // 추가 (PG사 거래번호)

    private LocalDateTime paymentDueAt; // 추가 (결제기한)

    private LocalDateTime paidAt;
}
