package com.developia.booksforeverybody.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookDto {
    private Long id;
    private String name;
    private String category;
    private String author;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private int count;
}
