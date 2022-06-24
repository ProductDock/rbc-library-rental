package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.domain.BookRentals;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookCopyRentalStateMapper {

    @Mapping(target = "email", source = "source.patron")
    BookCopyRentalStateDto toDto(BookRentals.BookCopyRentalState source);

    Collection<BookCopyRentalStateDto> toDtoCollection(Collection<BookRentals.BookCopyRentalState> source);
}
