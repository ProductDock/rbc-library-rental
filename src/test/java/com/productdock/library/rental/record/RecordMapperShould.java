package com.productdock.library.rental.record;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RecordMapperImpl.class})
class RecordMapperShould {

    @Autowired
    private RecordMapper recordMapper;

    @Test
    void mapRecordEntityToRecordDto() {
        var recordDTO = new RecordDTO("1", "RENT");
        var recordEntity = recordMapper.toEntity(recordDTO);
        assertThat(recordEntity.getBookId()).isEqualTo(recordDTO.bookId);
    }
}
