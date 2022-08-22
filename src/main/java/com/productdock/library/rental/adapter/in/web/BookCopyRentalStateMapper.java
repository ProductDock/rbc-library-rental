package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.domain.RentalWithUserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookCopyRentalStateMapper {

    BookCopyRentalStateDto toDto(RentalWithUserProfile source);

    Collection<BookCopyRentalStateDto> toDtoCollection(Collection<RentalWithUserProfile> source);
}
