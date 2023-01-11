package com.example.bookreviewapp.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long reviewId;
    private String reviewContent;
    private String reviewTitle;
    private Integer rating;
    private String bookIsbn;
    private String bookTile;
    private String bookThumbnailUrl;
    private String submittedBy;
    private LocalDateTime submittedAt;
}
