package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.domain.BookRentalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(target = "userEmail", source = "source.patron")
    BookInteraction toEntity(BookRentalRecord.BookCopy source);

    @Mapping(target = "patron", source = "source.userEmail")
    BookRentalRecord.BookCopy toDomain(BookInteraction source);
}
