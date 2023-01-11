package com.example.bookreviewapp.review;

import com.example.bookreviewapp.config.WebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void shouldReturnReviewStatisticsWhenUserIsAuthenticated() throws Exception {
        // Given

        // When
        mockMvc.perform(get("/api/books/reviews/statistics")
                .with(jwt()))
                .andExpect(status().isOk());

        // Then
        then(bookReviewService).should().getReviewStatistics();
    }

    @Test
    void shouldCreateNewBookReviewForAuthenticatedUserWithValidPayload() throws Exception {
        // Given
        String requestBody = """
      {
        "reviewTitle": "Great Java Book",
        "reviewContent": "I really like this book!",
        "rating": 4
      }
      """;
        String isbn = "9780321160768";
        String email = "john@spring.io";
        Long expectedBookReviewId = 84L;

        given(bookReviewService.createBookReview(eq(isbn), any(BookReviewRequest.class),
                eq(email))).willReturn(expectedBookReviewId);

        // When
        mockMvc.perform(post("/api/books/{isbn}/reviews", isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(jwt().jwt(builder -> builder.claim("email", email)
                        .claim("preferred_username", "john"))))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/books/%s/reviews/%s"
                        .formatted(isbn, expectedBookReviewId))));
    }
}