package com.example.bookreviewapp.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
