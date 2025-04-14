package com.tolm.bookstore.catalog.domain;

import com.tolm.bookstore.catalog.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    public PageResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageRequest = PageRequest.of(pageNo <= 1 ? 0 : pageNo - 1, applicationProperties.pageSize(), sort);
        Page<Product> productPage = productRepository.findAll(pageRequest).map(ProductMapper::toProduct);

        return new PageResult<>(
                productPage.getContent(),
                productPage.getTotalElements(),
                productPage.getNumber() + 1,
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast(),
                productPage.hasNext(),
                productPage.hasPrevious());
    }
}
