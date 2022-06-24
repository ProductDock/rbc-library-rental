package com.productdock.library.rental.adapter.out.mongo.mapper;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.domain.BookRentals;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(target = "userEmail", source = "source.patron")
    BookCopyRentalState toEntity(BookRentals.BookCopyRentalState source);

    @Mapping(target = "patron", source = "source.userEmail")
    BookRentals.BookCopyRentalState toDomain(BookCopyRentalState source);
}
