package com.productdock.library.rental.record;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RecordMapper {

    RecordDTO toDto(RecordEntity recordEntity);

    RecordEntity toEntity(RecordDTO recordDTO);

}
