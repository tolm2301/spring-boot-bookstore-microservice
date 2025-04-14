package com.tolm.bookstore.catalog.web.controllers;

import com.tolm.bookstore.catalog.domain.PageResult;
import com.tolm.bookstore.catalog.domain.Product;
import com.tolm.bookstore.catalog.domain.ProductService;
import com.tolm.bookstore.catalog.web.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
class ProductController {

    private final ProductService productService;

    @GetMapping
    PageResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(
            @PathVariable @Parameter(description = "The product's unique identifier", example = "P100") String code) {
        log.info("Fetching product for code: {}", code);
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
