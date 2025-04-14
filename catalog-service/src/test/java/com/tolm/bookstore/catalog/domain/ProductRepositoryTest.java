package com.tolm.bookstore.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.utility.TestcontainersConfiguration;

@DataJpaTest(
        properties = {
            "spring.test.database.replace=none",
        })
@Import(TestcontainersConfiguration.class)
@Sql("/test-data.sql")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(15);
    }

    @Test
    void shouldFindByCode() {
        assertTrue(productRepository.findByCode("P100").isPresent());
    }

    @Test
    void shouldNotFindNonExistingProduct() {
        assertTrue(productRepository.findByCode("non-existing-code").isEmpty());
    }
}
