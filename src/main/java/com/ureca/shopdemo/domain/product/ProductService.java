package com.ureca.shopdemo.domain.product;

import com.ureca.shopdemo.domain.admin.Admin;
import com.ureca.shopdemo.domain.admin.AdminRepository;
import com.ureca.shopdemo.domain.product.category.Category;
import com.ureca.shopdemo.domain.product.category.CategoryRepository;
import com.ureca.shopdemo.domain.product.dto.ProductCreateRequest;
import com.ureca.shopdemo.domain.product.dto.ProductResponse;
import com.ureca.shopdemo.domain.product.dto.ProductUpdateRequest;
import com.ureca.shopdemo.global.exception.ErrorCode;
import com.ureca.shopdemo.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;

    // ==================== Admin ====================
    // Todo: AdminService로 옮길지 고민하기
    // Todo: Paging

    // [우선순위: 상] 상품 생성
    public ProductResponse createProduct(ProductCreateRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));

        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new GeneralException(ErrorCode.ADMIN_NOT_FOUND));

        // 임시로 상품코드 생성
        String code = "P" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        Product product = productRepository.save(request.toEntity(category, admin, code));

        return ProductResponse.toDto(product);
    }

    // [우선순위: 상] 상품 수정
    public void updateProduct(Long id, ProductUpdateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorCode.PRODUCT_NOT_FOUND));

        product.updateProduct(request, category);
    }

    // [우선순위: 상] 상품 삭제 (소프트 딜리트)
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GeneralException(ErrorCode.PRODUCT_NOT_FOUND));

        product.softDeleteProduct();
    }

    // [우선순위: 상] 상품 조회 (검색 + 필터)
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAdminProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::toDto);
    }

    // [우선순위: 중] 상품 벌크 삽입 (CSV)
    public void bulkInsertProducts() {}

    // [우선순위: 중] 폐기 등록
    public void discardProduct() {}

    // ==================== User ====================

//    // [우선순위: 상] 상품 목록 조회 (카테고리, 정렬, 페이징)
//    @Transactional(readOnly = true)
//    public List<ProductResponse> getProducts() {}
//
//    // [우선순위: 상] 상품 상세 조회
//    @Transactional(readOnly = true)
//    public ProductResponse getProduct() {}
//
//    // [우선순위: 상] 상품 검색
//    @Transactional(readOnly = true)
//    public List<ProductResponse> searchProducts() {}
}

