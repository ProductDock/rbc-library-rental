package com.productdock.library.rental.adapter.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalEvent;
import com.productdock.library.rental.adapter.out.mongo.mapper.BookRentalEventMapper;
import com.productdock.library.rental.domain.BookRentalEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookRentalEventPersistenceAdapterShould {

    @InjectMocks
    private BookRentalEventPersistenceAdapter bookRentalEventRepository;

    @Mock
    private BookCopyRentalEventRepository bookCopyRentalEventRepository;

    @Mock
    private BookRentalEventMapper bookRentalEventMapper;


    private static final BookRentalEvent BOOK_RENTAL_EVENT = mock(BookRentalEvent.class);
    private static final BookCopyRentalEvent BOOK_COPY_RENTAL_EVENT = mock(BookCopyRentalEvent.class);

    @Test
    void saveBookRentalEvent() {
        given(bookRentalEventMapper.toEntity(BOOK_RENTAL_EVENT)).willReturn(BOOK_COPY_RENTAL_EVENT);

        bookRentalEventRepository.save(BOOK_RENTAL_EVENT);

        verify(bookCopyRentalEventRepository).save(BOOK_COPY_RENTAL_EVENT);
    }
}
