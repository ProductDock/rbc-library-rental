package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.service.RentalStatus.RENTED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RentalRecordServiceShould {

    private static final RentalRequestDto ANY_REQUEST_DTO = new RentalRequestDto("1", RENTED);
    private static final Optional<RentalRecordEntity> ANY_RENTAL_ENTITY = Optional.of(mock(RentalRecordEntity.class));
    private static final BookRentalRecord ANY_BOOK_RENTAL_RECORD = mock(BookRentalRecord.class);

    @InjectMocks
    private RentalRecordService rentalRecordService;

    @Mock
    private RentalRecordRepository rentalRecordRepository;

    @Mock
    private RentalRecordPublisher rentalRecordPublisher;

    @Mock
    private BookRentalRecordMapper bookRentalRecordMapper;

    @Test
    void verifyIfRentRecordEntityIsSavedAndPublished() throws Exception {
        given(rentalRecordRepository.findByBookId(ANY_REQUEST_DTO.bookId)).willReturn(ANY_RENTAL_ENTITY);
        given(bookRentalRecordMapper.toDomain(ANY_RENTAL_ENTITY.get())).willReturn(ANY_BOOK_RENTAL_RECORD);
        given(bookRentalRecordMapper.toEntity(ANY_BOOK_RENTAL_RECORD)).willReturn(ANY_RENTAL_ENTITY.get());

        rentalRecordService.create(ANY_REQUEST_DTO, any());

        InOrder inOrder = Mockito.inOrder(ANY_BOOK_RENTAL_RECORD, rentalRecordPublisher, rentalRecordRepository);
        inOrder.verify(ANY_BOOK_RENTAL_RECORD).trackActivity(any());
        inOrder.verify(rentalRecordRepository).save(ANY_RENTAL_ENTITY.get());
        inOrder.verify(rentalRecordPublisher).sendMessage(ANY_BOOK_RENTAL_RECORD);
    }
}
