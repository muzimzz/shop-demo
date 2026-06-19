package com.ureca.shopdemo.domain.product.dto;

import com.ureca.shopdemo.domain.admin.Admin;
import com.ureca.shopdemo.domain.product.category.Category;
import com.ureca.shopdemo.domain.product.Product;
import com.ureca.shopdemo.domain.product.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    private Long categoryId;

    private Long adminId;

    private String name;

    private int price;

    private int stockQuantity;

    private String detail;

    private LocalDateTime expirationDate;

    public Product toEntity(Category category, Admin admin, String code) {
        return Product.builder()
                .category(category)
                .admin(admin)
                .code(code)
                .name(this.name)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .detail(this.detail)
                .expirationDate(this.expirationDate)
                .status(this.stockQuantity == 0 ? ProductStatus.SOLD_OUT : ProductStatus.ON_SALE)
                .build();
    }
}
