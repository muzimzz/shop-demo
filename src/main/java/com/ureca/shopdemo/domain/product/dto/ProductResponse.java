package com.ureca.shopdemo.domain.product.dto;

import com.ureca.shopdemo.domain.product.Product;
import com.ureca.shopdemo.domain.product.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private Long categoryId;

    private Long adminId;

    private String code;

    private String name;

    private int price;

    private int stockQuantity;

    private String detail;

    private LocalDateTime expirationDate;

    private ProductStatus status;

    public static ProductResponse toDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .adminId(product.getAdmin().getId())
                .code(product.getCode())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .detail(product.getDetail())
                .expirationDate(product.getExpirationDate())
                .status(product.getStatus())
                .build();
    }
}
