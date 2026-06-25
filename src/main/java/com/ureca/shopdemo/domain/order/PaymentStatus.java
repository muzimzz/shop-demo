package com.ureca.shopdemo.domain.order;

public enum PaymentStatus {
    PENDING,    // 결제대기
    PAID,       // 결제완료
    CANCELLED,  // 취소
    REFUNDED    // 환불
}
