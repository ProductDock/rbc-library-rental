package com.productdock.library.rental.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRecordRepository extends MongoRepository<RentalRecordEntity, String> {

}
