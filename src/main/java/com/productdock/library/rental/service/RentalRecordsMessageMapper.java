package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BookCopyMapper.class})
public interface RentalRecordsMessageMapper {

    @Mappings({
            @Mapping(target = "rentalRecords", source = "source.bookCopies"),
    })
    RentalRecordsMessage toMessage(BookRentalRecord source);

    RentalRecordsMessage.RentalRecordRequest map(BookRentalRecord.BookCopy value);
}
