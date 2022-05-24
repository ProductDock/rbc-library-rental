package com.productdock.library.rental.ca.application.service;

import com.productdock.library.rental.ca.application.port.in.GetBookInteractionsQuery;
import com.productdock.library.rental.ca.domain.BookInteraction;
import com.productdock.library.rental.ca.domain.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
class GetBookInteractionsService implements GetBookInteractionsQuery {

    private final RentalRecordRepository rentalRecordRepository;

    public List<BookInteraction> getByBookId(String bookId) {
        Optional<RentalRecordEntity> recordEntity = rentalRecordRepository.findByBookId(bookId);
        if(recordEntity.isEmpty()){
            return new ArrayList<>();
        }
        return recordEntity.get().getInteractions();
    }
}
