package com.productdock.library.rental.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRecordRepository extends MongoRepository<RentalRecordEntity, String> {

    Optional<RentalRecordEntity> findByBookId(String bookId);
}
