package com.productdock.library.rental.adapter.out.mongo.mapper;

import com.productdock.library.rental.adapter.in.web.BookCopyRentalStateMapper;
import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;
import com.productdock.library.rental.domain.BookRentals;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BookCopyMapper.class, BookCopyRentalStateMapper.class})
public interface BookRentalRecordMapper {

    BookRentalState toEntity(BookRentals source);

    BookRentals toDomain(BookRentalState source);

    Collection<BookRentals> toDomainCollection(Collection<BookRentalState> source);
}
