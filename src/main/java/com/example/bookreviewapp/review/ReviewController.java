package com.example.bookreviewapp.review;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class ReviewController {

    private final BookReviewService bookReviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReviews(
            @RequestParam(name = "size", defaultValue = "20") Integer size,
            @RequestParam(name = "orderBy", defaultValue = "none") String orderBy) {

        List<ReviewResponse> bookReviews = bookReviewService.getAllReviews(size, orderBy);

        return ResponseEntity.ok(bookReviews);
    }

    @GetMapping("/reviews/statistics")
    public ResponseEntity<List<BookReviewStatisticResponse>> getReviewStatistics() {
        return ResponseEntity.ok(bookReviewService.getReviewStatistics());
    }

    @PostMapping("/{isbn}/reviews")
    public ResponseEntity<Void> createBookReview(@PathVariable String isbn, JwtAuthenticationToken jwt,
                                                 @RequestBody @Valid BookReviewRequest bookReviewRequest,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Long newBookReviewId = bookReviewService.createBookReview(isbn, bookReviewRequest, jwt.getTokenAttributes()
                .get("email").toString());

        UriComponents uriComponents = uriComponentsBuilder.path("/api/books/{isbn}/reviews/{reviewId}")
                .buildAndExpand(isbn, newBookReviewId);

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @DeleteMapping("/{isbn}/reviews/{bookReviewId}")
    public void deleteBookReview(@PathVariable String isbn, @PathVariable Long bookReviewId) {
        bookReviewService.deleteReview(isbn, bookReviewId);
    }
}
