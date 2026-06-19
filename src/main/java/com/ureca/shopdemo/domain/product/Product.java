package com.ureca.shopdemo.domain.product;

import com.ureca.shopdemo.domain.admin.Admin;
import com.ureca.shopdemo.domain.product.category.Category;
import com.ureca.shopdemo.domain.product.dto.ProductUpdateRequest;
import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    private String detail;

    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    public void updateProduct(ProductUpdateRequest request, Category category) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.stockQuantity = request.getStockQuantity();
        this.category = category;
        this.expirationDate = request.getExpirationDate();
        this.detail = request.getDetail();
        // 재고 0이면 무조건 품절, 아니면 요청한 status 적용
        this.status = this.stockQuantity == 0
                ? ProductStatus.SOLD_OUT
                : ProductStatus.valueOf(request.getStatus());
    }

    public void softDeleteProduct() {
        this.status = ProductStatus.DISCONTINUED;
    }
}
