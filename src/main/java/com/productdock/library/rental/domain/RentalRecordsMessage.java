package com.productdock.library.rental.domain;

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
public class RentalRecordsMessage implements Serializable {

    private String bookId;
    private List<RentalRecordRequest> rentalRecords;

    public RentalRecordsMessage(String bookId) {
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public List<RentalRecordRequest> getRentalRecords() {
        return rentalRecords;
    }

    public void setRentalRecords(List<RentalRecordRequest> rentalRecords) {
        this.rentalRecords = rentalRecords;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class RentalRecordRequest implements Serializable {

        private String patron;
        private RentalStatus status;
    }
}
