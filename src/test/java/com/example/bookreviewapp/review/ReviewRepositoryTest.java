package com.example.bookreviewapp.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.datasource.driver-class-name=com.p6spy.engine.spy.P6SpyDriver",
        "DB_CLOSE_DELAY=-1",
        "DB_CLOSE_ON_EXIT=false"

})
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("book_review")
            .withUsername("abran")
            .withPassword("s3cret");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:p6spy:postgresql://" + postgreSQLContainer.getHost() + ":" +
                postgreSQLContainer.getFirstMappedPort() + "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    private ReviewRepository cut;

    @Test
    @Sql(scripts = "/scripts/INIT_REVIEW_EACH_BOOK.sql") // changes by Sql are rolled back after test
    void shouldGetTwoReviewStatisticsWhenDatabaseContainsTwoBooksWithReview() {
        // When -> data provided through sql script file

        // When
        List<ReviewStatistic> result = cut.getReviewStatistics();

        // Then
        assertThat(cut.count()).isEqualTo(3);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getRatings()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(2);
    }




}