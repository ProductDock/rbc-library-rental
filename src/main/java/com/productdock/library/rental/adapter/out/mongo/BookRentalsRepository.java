package com.productdock.library.rental.adapter.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.mapper.BookRentalRecordMapper;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@Slf4j
@AllArgsConstructor
public class BookRentalsRepository implements BookRentalsPersistenceOutPort {

    private BookRentalStateRepository entityRepository;
    private BookRentalRecordMapper mapper;

    @Override
    public Optional<BookRentals> findByBookId(String bookId) {
        var entity = entityRepository.findByBookId(bookId);
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapper.toDomain(entity.get()));
    }

    @Override
    public Collection<BookRentals> findWithReservations() {
        var reservedRentalRecords = entityRepository.findWithReservations();
        return mapper.toDomainCollection(reservedRentalRecords);
    }

    @Override
    public void save(BookRentals bookRentals) {
        log.debug("Save new book's rental record in database with id : {}", bookRentals);
        var previousRecordEntity = entityRepository.findByBookId(bookRentals.getBookId());
        var newRecordEntity = mapper.toEntity(bookRentals);
        if (previousRecordEntity.isPresent()) {
            newRecordEntity.setId(previousRecordEntity.get().getId());
        }
        entityRepository.save(newRecordEntity);
    }

}
