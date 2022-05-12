package com.productdock.library.rental.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {BookRecordMapper.class})
public interface RentalRecordDtoMapper {

    @Mapping(target = "records", source = "source.interactions")
    RentalRecordsDto toDto(RentalRecordEntity source);

}
