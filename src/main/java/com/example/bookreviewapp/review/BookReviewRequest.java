package com.example.bookreviewapp.review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record BookReviewRequest(@NotEmpty String reviewTitle,
                                @NotEmpty String reviewContent,
                                @NotNull @PositiveOrZero Integer rating) {
}
