package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.kafka.RentalsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.service.RentalStatus.RENTED;
import static com.productdock.library.rental.service.RentalStatus.RESERVED;
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
    private RentalsPublisher publisher;

    @Mock
    private BookRentalRecordMapper bookRentalRecordMapper;

    @Mock
    private RentalRecordsMessageMapper rentalRecordsMessageMapper;

    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InNvbWFpZHZlc3RhQHByb2R1Y3Rkb2NrLmNvbSIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.gbjuSfsxUowv8kY5b-wwMjiv82e-syanUpEmWZ3Vp6c";

    @BeforeEach
    final void before() {
        rentalRecordRepository.deleteAll();
    }

    @Test
    void verifyRentRecordEntityIsSavedAndPublished() throws Exception {
        var recordDTO = new RentalRequestDto("1", RENTED);
        var recordEntity = Optional.of(mock(RentalRecordEntity.class));
        var bookRentalRecord = mock(BookRentalRecord.class);

        given(rentalRecordRepository.findById(recordDTO.bookId)).willReturn(recordEntity);
        given(bookRentalRecordMapper.toDomain(recordEntity.get())).willReturn(bookRentalRecord);
        given(bookRentalRecordMapper.toEntity(bookRentalRecord)).willReturn(recordEntity.get());

        rentalRecordService.create(recordDTO, token);

        verify(publisher).sendMessage(rentalRecordsMessageMapper.toMessage(bookRentalRecord));
        verify(rentalRecordRepository).save(recordEntity.get());
    }

    @Test
    void verifyReserveRecordEntityIsSavedAndPublished() throws Exception {
        var recordDTO = new RentalRequestDto("1", RESERVED);
        var recordEntity = Optional.of(mock(RentalRecordEntity.class));
        var bookRentalRecord = mock(BookRentalRecord.class);

        given(rentalRecordRepository.findById(recordDTO.bookId)).willReturn(recordEntity);
        given(bookRentalRecordMapper.toDomain(recordEntity.get())).willReturn(bookRentalRecord);
        given(bookRentalRecordMapper.toEntity(bookRentalRecord)).willReturn(recordEntity.get());

        rentalRecordService.create(recordDTO, token);

        verify(publisher).sendMessage(rentalRecordsMessageMapper.toMessage(bookRentalRecord));
        verify(rentalRecordRepository).save(recordEntity.get());
    }
}
