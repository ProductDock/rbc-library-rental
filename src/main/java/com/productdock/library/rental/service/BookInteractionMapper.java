package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.domain.BookRentalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookInteractionMapper {

    @Mappings({
            @Mapping(target="userEmail", source="source.patron"),
            @Mapping(target="date", source="source.date")
    })
    BookInteraction toEntity(BookRentalRecord.BookCopy source);

    @Mappings({
            @Mapping(target="patron", source="source.userEmail"),
            @Mapping(target="date", source="source.date")
    })
    BookRentalRecord.BookCopy to(BookInteraction source);
}
