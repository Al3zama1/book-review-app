package com.example.bookreviewapp.review;

import com.example.bookreviewapp.config.WebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@Import(WebSecurityConfig.class)
class ReviewControllerTest {

    @MockBean
    private BookReviewService bookReviewService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTwentyReviewsWithoutAnyOrderWhenNoParametersAreSpecified() throws Exception {
        // Given
        ReviewResponse reviewResponse = ReviewResponse.builder()
                .reviewId(1L)
                .reviewContent("This is the review content")
                .reviewTitle("This is the review title")
                .rating(5)
                .bookIsbn("9780321160768")
                .bookTile("This is the book title")
                .bookThumbnailUrl("https://covers.openlibrary.org/b/id/388761-S.jpg")
                .submittedBy("Abran")
                .submittedAt(LocalDateTime.now())
                .build();

        List<ReviewResponse> bookReviews = List.of(reviewResponse);

        given(bookReviewService.getAllReviews(20, "none")).willReturn(bookReviews);

        // When
        mockMvc.perform(get("/api/books/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void shouldNotReturnReviewStatisticsWhenUserIsUnauthenticated() throws Exception {
        // Given

        // When
        mockMvc.perform(get("/api/books/reviews/statistics"))
                .andExpect(status().isUnauthorized());

        // Then
        then(bookReviewService).shouldHaveNoInteractions();
    }
}