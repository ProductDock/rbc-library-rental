package com.productdock.library.rental.adapter.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRentalEventRepository extends MongoRepository<BookCopyRentalEvent, String> {

}
