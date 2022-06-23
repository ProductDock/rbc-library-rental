package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.domain.BookRentalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookRecordMapper {

    @Mapping(target = "email", source = "source.patron")
    BookRecordDto toDto(BookRentalRecord.BookCopy source);

    Collection<BookRecordDto> toDtoCollection(Collection<BookRentalRecord.BookCopy> source);
}
