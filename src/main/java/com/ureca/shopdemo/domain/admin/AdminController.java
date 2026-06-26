package com.ureca.shopdemo.domain.admin;

import com.ureca.shopdemo.domain.member.MemberService;
import com.ureca.shopdemo.domain.product.ProductService;
import com.ureca.shopdemo.domain.product.dto.ProductCreateRequest;
import com.ureca.shopdemo.domain.product.dto.ProductResponse;
import com.ureca.shopdemo.domain.product.dto.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    // AdminService로 옮길 지 추후에 고민
    private final ProductService productService;
    private final MemberService  memberService;

    // ==================== Admin ====================

    // 상품 생성
    @PostMapping("/product")
    public ResponseEntity<ProductResponse> createProduct(ProductCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request));

    }

    // 상품 수정
    @PutMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id,
                                              @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(id, request);
        return ResponseEntity.ok().build();
    }

    // [우선순위: 상] 상품 삭제 (소프트 딜리트)
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

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

    // ==================== Member Management ====================

    // 회원 목록 조회 (페이징)
    @GetMapping("/member")
    public void getMembers(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {}

    // 회원 상세 조회
    @GetMapping("/member/{id}")
    public void getMember(@PathVariable Long id) {}

    // 회원 차단
    @PatchMapping("/member/{id}/block")
    public void blockMember(@PathVariable Long id) {}

    // 회원 차단 해제
    @PatchMapping("/member/{id}/unblock")
    public void unblockMember(@PathVariable Long id) {}

    // 강제 탈퇴
    @DeleteMapping("/member/{id}")
    public void forceWithdraw(@PathVariable Long id) {}
}
