package com.ureca.shopdemo.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    // Admin용 기능은 AdminController에

    // ==================== User ====================

    // [우선순위: 상] 상품 목록 조회 (카테고리, 정렬, 페이징)
    // GET /product

    // [우선순위: 상] 상품 상세 조회
    // GET /product/{productId}

    // [우선순위: 상] 상품 검색
    // GET /product/search
}
