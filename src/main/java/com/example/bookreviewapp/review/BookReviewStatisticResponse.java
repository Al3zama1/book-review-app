package com.example.bookreviewapp.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewStatisticResponse {
    private Long bookId;
    private String isbn;
    private BigDecimal avg;
    private Long ratings;
}
