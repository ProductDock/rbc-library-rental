package com.productdock.library.rental.adapter.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface BookRentalStateRepository extends MongoRepository<BookRentalState, String> {

    Optional<BookRentalState> findByBookId(String bookId);

    @Query("{ 'bookCopiesRentalState.status' : 'RESERVED' }")
    Collection<BookRentalState> findWithReservations();

}
