package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = { RentalRecordsMessageMapper.class })
public interface RentalRecordRequestMapper {

    RentalRecordsMessage.RentalRecordRequest toMessage(BookRentalRecord.BookCopy source);
}
