package com.productdock.library.rental.service;

import com.productdock.library.rental.producer.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.service.RentalStatus.RENT;
import static com.productdock.library.rental.service.RentalStatus.RESERVE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RentalRecordServiceShould {

    @InjectMocks
    private RentalRecordService rentalRecordService;

    @Mock
    private RentalRecordRepository rentalRecordRepository;

    @Mock
    private RentalRecordMapper recordMapper;

    @Mock
    private Publisher publisher;

    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InNvbWFpZHZlc3RhQHByb2R1Y3Rkb2NrLmNvbSIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.gbjuSfsxUowv8kY5b-wwMjiv82e-syanUpEmWZ3Vp6c";

    @BeforeEach
    final void before() {
        rentalRecordRepository.deleteAll();
    }

    @Test
    void verifyRentRecordEntityIsSavedAndPublished() {
        var recordDTO = new RentalRecordDto("1", RENT);
        var recordEntity = Optional.of(mock(RentalRecordEntity.class));

        given(rentalRecordRepository.findById(recordDTO.bookId)).willReturn(recordEntity);

        rentalRecordService.create(recordDTO, token);

        verify(publisher).sendMessage(recordEntity.get());
        verify(rentalRecordRepository).save(recordEntity.get());
    }

    void verifyReserveRecordEntityIsSavedAndPublished() {
        var recordDTO = new RentalRecordDto("1", RESERVE);
        var recordEntity = Optional.of(mock(RentalRecordEntity.class));

        given(rentalRecordRepository.findById(recordDTO.bookId)).willReturn(recordEntity);

        rentalRecordService.create(recordDTO, token);

        verify(publisher).sendMessage(recordEntity.get());
        verify(rentalRecordRepository).save(recordEntity.get());
    }
}
