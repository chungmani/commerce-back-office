package com.example.commercebackoffice.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryProductCount {

    private String category;
    private long count;
}
