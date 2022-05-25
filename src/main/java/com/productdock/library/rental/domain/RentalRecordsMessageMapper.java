package com.productdock.library.rental.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RentalRecordsMessageMapper {

    @Mapping(target = "rentalRecords", source = "source.bookCopies")
    RentalRecordsMessage toMessage(BookRentalRecord source);
}
