package com.productdock.library.rental.adapter.out.mongo.mapper;

import com.productdock.library.rental.domain.BookRentalEvent;
import com.productdock.library.rental.domain.RentalAction;
import com.productdock.library.rental.domain.RentalActionType;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class BookRentalEventMapperShould {

    private final BookRentalEventMapper bookRentalEventMapper = Mappers.getMapper(BookRentalEventMapper.class);

    @Test
    void mapDomainBookRentalEventToEntityBookCopyRentalEvent() {
        var domainRentalEvent = new BookRentalEvent(new RentalAction("1", "test@test.com", RentalActionType.RENT));

        var entityRentalEvent = bookRentalEventMapper.toEntity(domainRentalEvent);

        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(entityRentalEvent.getBookId()).isEqualTo(domainRentalEvent.bookId);
            softly.assertThat(entityRentalEvent.getUserId()).isEqualTo(domainRentalEvent.userId);
            softly.assertThat(entityRentalEvent.getAction()).isEqualTo(domainRentalEvent.action);
            softly.assertThat(entityRentalEvent.getDate()).isEqualTo(domainRentalEvent.date);
        }
    }
}
