package com.productdock.library.rental.record;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RecordMapper {

    RecordEntity toEntity(RecordDTO recordDTO);

}
