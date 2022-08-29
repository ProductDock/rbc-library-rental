package com.productdock.library.rental.adapter.out.mongo.mapper;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalEvent;
import com.productdock.library.rental.domain.BookRentalEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookRentalEventMapper {

    BookCopyRentalEvent toEntity(BookRentalEvent source);
}
