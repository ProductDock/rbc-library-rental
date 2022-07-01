package com.productdock.library.rental.adapter.out.kafka.mapper;

import com.productdock.library.rental.adapter.out.kafka.BookRentalsMessage;
import com.productdock.library.rental.domain.BookRentals;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookRentalsMessageMapper {

    @Mapping(target = "rentalRecords", source = "source.bookCopiesRentalState")
    BookRentalsMessage toMessage(BookRentals source);
}
