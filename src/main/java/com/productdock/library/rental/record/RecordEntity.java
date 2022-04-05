package com.productdock.library.rental.record;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordEntity {
    private Long id;
    private String status;
}
