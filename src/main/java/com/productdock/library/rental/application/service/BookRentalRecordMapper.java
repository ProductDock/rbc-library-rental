package com.productdock.library.rental.application.service;

import com.productdock.library.rental.domain.RentalRecordEntity;
import com.productdock.library.rental.domain.BookRentalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BookCopyMapper.class, BookRecordMapper.class})
public interface BookRentalRecordMapper {

    @Mapping(target = "interactions", source = "source.bookCopies")
    RentalRecordEntity toEntity(BookRentalRecord source);

    @Mapping(target = "bookCopies", source = "source.interactions")
    BookRentalRecord toDomain(RentalRecordEntity source);
}
