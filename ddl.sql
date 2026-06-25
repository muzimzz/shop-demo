-- ============================================================
-- 회원
-- ============================================================

CREATE TABLE member (
    member_id       BIGINT          NOT NULL AUTO_INCREMENT,
    login_id        VARCHAR(50)     NOT NULL,	# 이름/이메일/전화번호 암호화는 따로 고려
    password_hash   VARCHAR(255)    NOT NULL,
    name            VARCHAR(50)     NOT NULL,	# grade 제외하였음
    email           VARCHAR(100)    NOT NULL,
    phone           VARCHAR(20)     NOT NULL,
    marketing_agreed TINYINT(1)     NOT NULL DEFAULT 0,
    status          VARCHAR(20)     NOT NULL,	# java에서 enumtype=string으로 변환
    is_blocked      TINYINT(1)      NOT NULL DEFAULT 0,
    blocked_at      DATETIME        NULL,
    deleted_at      DATETIME        NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (member_id),
    UNIQUE KEY uq_member_login_id  (login_id),
    UNIQUE KEY uq_member_email     (email),
    UNIQUE KEY uq_member_phone     (phone)
);

-- ============================================================
-- 배송지
-- ============================================================

CREATE TABLE address (
    address_id      BIGINT          NOT NULL AUTO_INCREMENT,
    member_id       BIGINT          NOT NULL,
    recipient       VARCHAR(50)     NOT NULL,
    phone           VARCHAR(20)     NOT NULL,
    zip_code        VARCHAR(10)     NOT NULL,
    road_address    VARCHAR(255)    NOT NULL,
    detail_address  VARCHAR(255)    NULL,
    is_default      TINYINT(1)      NOT NULL DEFAULT 0,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (address_id),
    CONSTRAINT fk_address_member
        FOREIGN KEY (member_id) REFERENCES member (member_id)
);

-- ============================================================
-- 장바구니
-- ============================================================

CREATE TABLE cart (		# created_at, updated_at 넣어야하는지?
    cart_id         BIGINT          NOT NULL AUTO_INCREMENT,
    member_id       BIGINT          NOT NULL,	
    PRIMARY KEY (cart_id),
    UNIQUE KEY uq_cart_member (member_id),
    CONSTRAINT fk_cart_member
        FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE cart_item (
    cart_item_id    BIGINT          NOT NULL AUTO_INCREMENT,
    cart_id         BIGINT          NOT NULL,
    product_id      BIGINT          NOT NULL,
	quantity		INT             NOT NULL DEFAULT 1,
    added_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (cart_item_id),
    UNIQUE KEY uq_cart_item (cart_id, product_id),
    CONSTRAINT fk_cart_item_cart
        FOREIGN KEY (cart_id) REFERENCES cart (cart_id),
    CONSTRAINT fk_cart_item_product
        FOREIGN KEY (product_id) REFERENCES product (product_id)
);

-- ============================================================
-- 주문
-- ============================================================

CREATE TABLE orders (
    order_id            BIGINT          NOT NULL AUTO_INCREMENT,
    order_no            VARCHAR(50)     NOT NULL,
    member_id           BIGINT          NOT NULL,
    status              VARCHAR(20)     NOT NULL,
    product_amount      INT             NOT NULL,
    discount_amount     INT             NOT NULL DEFAULT 0,
    shipping_fee        INT             NOT NULL DEFAULT 0,
    total_amount        INT             NOT NULL,
    ship_recipient      VARCHAR(50)     NOT NULL,
    ship_phone          VARCHAR(20)     NOT NULL,
    ship_zip_code       VARCHAR(10)     NOT NULL,
    ship_address        VARCHAR(255)    NOT NULL,
    ship_detail_address VARCHAR(255)    NULL,
    ship_message        VARCHAR(255)    NULL,
    ordered_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (order_id),
    UNIQUE KEY uq_orders_order_no (order_no),
    CONSTRAINT fk_orders_member
        FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE order_item (
    order_item_id       BIGINT          NOT NULL AUTO_INCREMENT,
    order_id            BIGINT          NOT NULL,
    product_id          BIGINT          NOT NULL,
    product_name VARCHAR(255)  NOT NULL,
    unit_price          INT             NOT NULL,
    quantity            INT             NOT NULL,
    item_status         VARCHAR(20)     NOT NULL,
    created_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (order_item_id),
    CONSTRAINT fk_order_item_order
        FOREIGN KEY (order_id) REFERENCES orders (order_id),
    CONSTRAINT fk_order_item_product
        FOREIGN KEY (product_id) REFERENCES product (product_id)
);

CREATE TABLE order_status_history (
    history_id      BIGINT          NOT NULL AUTO_INCREMENT,
    order_id        BIGINT          NOT NULL,
    from_status     VARCHAR(50)     NULL,
    to_status       VARCHAR(50)     NOT NULL,
    changed_by      VARCHAR(20)     NOT NULL,
    changed_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (history_id),
    CONSTRAINT fk_order_status_history_order
        FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

-- ============================================================
-- 결제
-- ============================================================

CREATE TABLE payment (
    payment_id      BIGINT          NOT NULL AUTO_INCREMENT,
    order_id        BIGINT          NOT NULL,
    method          VARCHAR(20)     NOT NULL,
    amount          INT             NOT NULL,
    status          VARCHAR(20)     NOT NULL,
    pg_tid          VARCHAR(100)    NULL,
    payment_due_at  DATETIME        NULL,
    paid_at         DATETIME        NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (payment_id),
    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

-- ============================================================
-- 환불
-- ============================================================

CREATE TABLE refund (
    refund_id           BIGINT          NOT NULL AUTO_INCREMENT,
    payment_id          BIGINT          NOT NULL,
    claim_id            BIGINT          NOT NULL,
    amount              INT             NOT NULL,
    shipping_deduction  INT             NOT NULL DEFAULT 0,
    status              VARCHAR(20)     NOT NULL,
    refunded_at         DATETIME        NULL,
    created_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (refund_id),
    CONSTRAINT fk_refund_payment
        FOREIGN KEY (payment_id) REFERENCES payment (payment_id),
    CONSTRAINT fk_refund_claim
        FOREIGN KEY (claim_id) REFERENCES claim (claim_id)
);

-- ============================================================
-- 클레임 (취소/반품/교환)
-- ============================================================

CREATE TABLE claim (
    claim_id        BIGINT          NOT NULL AUTO_INCREMENT,
    order_id        BIGINT          NOT NULL,
    type            VARCHAR(20)     NOT NULL,
    status          VARCHAR(20)     NOT NULL,
    reason          VARCHAR(255)    NULL,
    reason_type     VARCHAR(20)     NOT NULL,
    processed_by    BIGINT          NULL,
    requested_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at    DATETIME        NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (claim_id),
    CONSTRAINT fk_claim_order
        FOREIGN KEY (order_id) REFERENCES orders (order_id),
    CONSTRAINT fk_claim_admin
        FOREIGN KEY (processed_by) REFERENCES admin (admin_id)
);

CREATE TABLE claim_item (
    claim_item_id   BIGINT          NOT NULL AUTO_INCREMENT,
    claim_id        BIGINT          NOT NULL,
    order_item_id   BIGINT          NOT NULL,
    qty             INT             NOT NULL,
    PRIMARY KEY (claim_item_id),
    CONSTRAINT fk_claim_item_claim
        FOREIGN KEY (claim_id) REFERENCES claim (claim_id),
    CONSTRAINT fk_claim_item_order_item
        FOREIGN KEY (order_item_id) REFERENCES order_item (order_item_id)
);

-- ============================================================
-- 배송
-- ============================================================

CREATE TABLE shipment (
    shipment_id     BIGINT          NOT NULL AUTO_INCREMENT,
    order_id        BIGINT          NULL,
    claim_id        BIGINT          NULL,
    carrier         VARCHAR(50)     NULL,
    tracking_no     VARCHAR(100)    NULL,
    status          VARCHAR(20)     NOT NULL,
    shipped_at      DATETIME        NULL,
    delivered_at    DATETIME        NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (shipment_id),
    CONSTRAINT fk_shipment_order
        FOREIGN KEY (order_id) REFERENCES orders (order_id),
    CONSTRAINT fk_shipment_claim
        FOREIGN KEY (claim_id) REFERENCES claim (claim_id)
);

-- ============================================================
-- 재고 할당 (lot 역추적용)
-- ============================================================

CREATE TABLE stock_allocation (
    allocation_id   BIGINT          NOT NULL AUTO_INCREMENT,
    order_item_id   BIGINT          NOT NULL,
    lot_id          BIGINT          NOT NULL,
    quantity        INT             NOT NULL,
    allocated_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (allocation_id),
    CONSTRAINT fk_stock_allocation_order_item
        FOREIGN KEY (order_item_id) REFERENCES order_item (order_item_id),
    CONSTRAINT fk_stock_allocation_lot
        FOREIGN KEY (lot_id) REFERENCES stock_lot (lot_id)
);

-- ============================================================
-- 알림
-- ============================================================

CREATE TABLE notification (
    notification_id BIGINT          NOT NULL AUTO_INCREMENT,
    member_id       BIGINT          NOT NULL,
    order_id        BIGINT          NULL,
    channel         VARCHAR(20)     NOT NULL,
    type            VARCHAR(20)     NOT NULL,
    content         VARCHAR(255)    NULL,
    status          VARCHAR(20)     NOT NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (notification_id),
    CONSTRAINT fk_notification_member
        FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT fk_notification_order
        FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

-- ============================================================
-- 감사 로그
-- ============================================================

CREATE TABLE audit_log (
    log_id          BIGINT          NOT NULL AUTO_INCREMENT,
    admin_id        BIGINT          NOT NULL,
    action          VARCHAR(50)     NOT NULL,
    target          VARCHAR(50)     NOT NULL,
    detail          VARCHAR(255)    NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (log_id),
    CONSTRAINT fk_audit_log_admin
        FOREIGN KEY (admin_id) REFERENCES admin (admin_id)
);
