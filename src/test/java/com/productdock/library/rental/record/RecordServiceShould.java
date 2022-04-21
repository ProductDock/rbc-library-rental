package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InNvbWFpZHZlc3RhQHByb2R1Y3Rkb2NrLmNvbSIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.gbjuSfsxUowv8kY5b-wwMjiv82e-syanUpEmWZ3Vp6c";

    @BeforeEach
    final void before() {
        recordRepository.deleteAll();
    }

    @Test
    void verifyRentRecordEntityIsSavedAndPublished() {
        var recordDTO = new RecordDTO("1", "RENT");
        var recordEntity = Optional.of(mock(RecordEntity.class));

        given(recordRepository.findById(recordDTO.bookId)).willReturn(recordEntity);

        recordService.create(recordDTO, token);

        verify(publisher).sendMessage(recordEntity.get());
        verify(recordRepository).save(recordEntity.get());
    }

    void verifyReserveRecordEntityIsSavedAndPublished() {
        var recordDTO = new RecordDTO("1", "RESERVE");
        var recordEntity = Optional.of(mock(RecordEntity.class));

        given(recordRepository.findById(recordDTO.bookId)).willReturn(recordEntity);

        recordService.create(recordDTO, token);

        verify(publisher).sendMessage(recordEntity.get());
        verify(recordRepository).save(recordEntity.get());
    }
}
