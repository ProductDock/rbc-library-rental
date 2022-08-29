package com.productdock.library.rental.adapter.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.mapper.BookRentalEventMapper;
import com.productdock.library.rental.application.port.out.persistence.BookRentalEventPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentalEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class BookRentalEventPersistenceAdapter implements BookRentalEventPersistenceOutPort {

    private BookCopyRentalEventRepository entityRepository;
    private BookRentalEventMapper mapper;

    @Override
    public void save(BookRentalEvent bookRentalEvent) {
        var entity = mapper.toEntity(bookRentalEvent);
        entityRepository.save(entity);
    }
}
