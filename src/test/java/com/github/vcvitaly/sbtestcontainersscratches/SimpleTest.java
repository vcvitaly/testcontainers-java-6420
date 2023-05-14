package com.github.vcvitaly.sbtestcontainersscratches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * SimpleTest.
 *
 * @author Vitalii Chura
 */
@SpringBootTest
@Testcontainers
class SimpleTest {

    private static final PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.9");

    @BeforeAll
    static void beforeAll() {
        postgreSqlContainer.start();
    }

    @Test
    void test() {
        Assertions.assertEquals(1, 1);
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSqlContainer::getPassword);
        registry.add("spring.datasource.username", postgreSqlContainer::getUsername);
    }
}
