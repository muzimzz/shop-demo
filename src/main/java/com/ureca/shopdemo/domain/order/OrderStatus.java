package com.ureca.shopdemo.domain.order;

public enum OrderStatus {
    PAYMENT_PENDING,   // 결제대기
    PAYMENT_DONE,      // 결제완료
    PREPARING,         // 상품준비중
    READY_TO_SHIP,     // 배송준비
    SHIPPING,          // 배송중
    DELIVERED,         // 배송완료
    CANCELLED,         // 취소
    RETURN_REQUESTED,  // 반품요청
    RETURN_DONE        // 반품완료
}
