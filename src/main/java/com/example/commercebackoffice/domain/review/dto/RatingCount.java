package com.example.commercebackoffice.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RatingCount {

    private int rating;
    private long count;
}
