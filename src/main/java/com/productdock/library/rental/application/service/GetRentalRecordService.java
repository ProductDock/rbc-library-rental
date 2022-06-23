package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.GetRentalRecordQuery;
import com.productdock.library.rental.application.port.out.persistence.RentalRecordPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentalRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
class GetRentalRecordService implements GetRentalRecordQuery {

    private RentalRecordPersistenceOutPort rentalRecordRepository;

    @Override
    public Collection<BookRentalRecord.BookCopy> getBookCopiesByBookId(String bookId) {
        log.debug("Get book copies from rental record for the {} book", bookId);
        var record = rentalRecordRepository.findByBookId(bookId);
        if (record.isEmpty()) {
            return new ArrayList<>();
        }
        return record.get().getBookCopies();
    }
}
