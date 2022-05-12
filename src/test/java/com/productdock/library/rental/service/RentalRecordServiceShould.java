package com.productdock.library.rental.service;

import com.productdock.library.rental.domain.BookRentalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Optional;

import static com.productdock.library.rental.service.RentalStatus.RENTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RentalRecordServiceShould {

    public static final String DEFAULT_BOOK_ID = "1";
    private static final RentalRequestDto ANY_REQUEST_DTO = new RentalRequestDto(DEFAULT_BOOK_ID, RENTED);
    private static final Optional<RentalRecordEntity> ANY_RENTAL_ENTITY = Optional.of(mock(RentalRecordEntity.class));
    private static final BookRentalRecord ANY_BOOK_RENTAL_RECORD = mock(BookRentalRecord.class);
    public static final Collection ANY_BOOK_RECORD_DTO_COLLECTION = mock(Collection.class);

    @InjectMocks
    private RentalRecordService rentalRecordService;

    @Mock
    private RentalRecordRepository rentalRecordRepository;

    @Mock
    private RentalRecordPublisher rentalRecordPublisher;

    @Mock
    private BookRentalRecordMapper bookRentalRecordMapper;

    @Mock
    private BookRecordMapper bookRecordMapper;

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

    @Test
    void getBookRentalRecords_whenMissingRecords() {
        given(rentalRecordRepository.findById(DEFAULT_BOOK_ID)).willReturn(Optional.empty());

        var bookRecords = rentalRecordService.getByBookId(DEFAULT_BOOK_ID);

        assertThat(bookRecords).isEmpty();
    }

    @Test
    void getBookRentalRecords() {
        given(rentalRecordRepository.findById(DEFAULT_BOOK_ID)).willReturn(ANY_RENTAL_ENTITY);
        given(bookRecordMapper.toDtoCollection(ANY_RENTAL_ENTITY.get().getInteractions()))
                .willReturn(ANY_BOOK_RECORD_DTO_COLLECTION);

        var bookRecords = rentalRecordService.getByBookId(DEFAULT_BOOK_ID);

        assertThat(bookRecords).isEqualTo(ANY_BOOK_RECORD_DTO_COLLECTION);
    }
}
