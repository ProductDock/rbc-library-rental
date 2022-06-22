package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BookCopyMapper.class, BookRecordMapper.class})
public interface BookRentalRecordMapper {

    @Mapping(target = "interactions", source = "source.bookCopies")
    RentalRecordEntity toEntity(BookRentalRecord source);

    @Mapping(target = "bookCopies", source = "source.interactions")
    BookRentalRecord toDomain(RentalRecordEntity source);

    Collection<BookRentalRecord> toDomainCollection(Collection<RentalRecordEntity> source);
}
