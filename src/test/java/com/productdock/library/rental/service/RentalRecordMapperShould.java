package com.productdock.library.rental.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.productdock.library.rental.service.RentalStatus.RENTED;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RentalRecordMapperImpl.class})
class RentalRecordMapperShould {

    @Autowired
    private RentalRecordMapper recordMapper;

    @Test
    void mapRecordEntityToRecordDto() {
        var recordDTO = new RentalRequest("1", RENTED);
        var recordEntity = recordMapper.toEntity(recordDTO);
        assertThat(recordEntity.getBookId()).isEqualTo(recordDTO.bookId);
    }
}
