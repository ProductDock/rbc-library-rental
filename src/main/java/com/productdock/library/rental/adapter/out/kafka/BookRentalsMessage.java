package com.productdock.library.rental.adapter.out.kafka;

import com.productdock.library.rental.domain.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRentalsMessage implements Serializable {

    private String bookId;
    private List<RentalRecord> rentalRecords;

    public BookRentalsMessage(String bookId) {
        this.bookId = bookId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class RentalRecord implements Serializable {

        private String patron;
        private RentalStatus status;
    }
}
