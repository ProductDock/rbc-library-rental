package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookRecordMapper {

    @Mapping(target = "email", source = "source.userEmail")
    BookRecordDto toDto(BookInteraction source);

}
