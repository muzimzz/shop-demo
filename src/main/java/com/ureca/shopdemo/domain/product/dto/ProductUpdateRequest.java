package com.ureca.shopdemo.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductUpdateRequest {

    private String name;

    private String status;

    private int price;

    private int stockQuantity;

    private Long categoryId;

    private LocalDateTime expirationDate;

    private String detail;
}
