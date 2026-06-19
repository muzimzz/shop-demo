package com.ureca.shopdemo.domain.admin;

import com.ureca.shopdemo.domain.product.ProductService;
import com.ureca.shopdemo.domain.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    // AdminService로 옮길 지 추후에 고민
    private final ProductService productService;

    // ==================== Admin ====================

    // [우선순위: 상] 상품 생성
    // POST /product

    // [우선순위: 상] 상품 수정
    // PUT /product/{productId}

    // [우선순위: 상] 상품 삭제 (소프트 딜리트)
    // DELETE /product/{productId}

    // [우선순위: 상] 상품 조회 (검색 + 필터)
    // GET /product/admin
    @GetMapping("/product")
    public ResponseEntity<Page<ProductResponse>> findAllProducts(
            @PageableDefault(size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getAdminProducts(pageable));
    }

    // [우선순위: 중] 상품 벌크 삽입 (CSV)
    // POST /product/bulk

    // [우선순위: 중] 폐기 등록
    // POST /product/{productId}/discard
}
