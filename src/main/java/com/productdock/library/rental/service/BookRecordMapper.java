package com.productdock.library.rental.service;

import com.productdock.library.rental.ca.domain.BookInteraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookRecordMapper {

    @Mapping(target = "email", source = "source.userEmail")
    BookRecordDto toDto(BookInteraction source);

    Collection<BookRecordDto> toDtoCollection(Collection<BookInteraction> source);
}
