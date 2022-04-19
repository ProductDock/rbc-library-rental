package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.Flow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecordServiceShould {

    @InjectMocks
    private RecordService recordService;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private RecordMapper recordMapper;

    @Mock
    private Publisher publisher;

    @BeforeEach
    final void before() {
        recordRepository.deleteAll();
    }

    @Test
    void verifyRecordEntityIsSavedAndPublished() {
        var recordDTO = new RecordDTO("1", "RENT");
        var recordEntity = Optional.of(mock(RecordEntity.class));

        given(recordRepository.findById(recordDTO.bookId)).willReturn(recordEntity);

        recordService.create(recordDTO);

        verify(publisher).sendMessage(recordEntity.get());
        verify(recordRepository).save(recordEntity.get());
    }
}
