package com.productdock.library.rental.service;

import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static com.productdock.library.rental.data.provider.BookInteractionMother.defaultBookInteraction;
import static com.productdock.library.rental.data.provider.BookInteractionMother.defaultBookInteractionBuilder;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookRecordMapperImpl.class})
public class BookRecordMapperShould {

    @Autowired
    private BookRecordMapper bookRecordMapper;

    @Test
    void mapBookInteractionToBookRecordDto() {
        var bookInteraction = defaultBookInteraction();

        var bookRecordDto = bookRecordMapper.toDto(bookInteraction);

        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(bookRecordDto.email).isEqualTo(bookInteraction.getUserEmail());
            softly.assertThat(bookRecordDto.status).isEqualTo(bookInteraction.getStatus());
        }
    }

    @Test
    void mapBookInteractionCollectionToBookRecordDtoCollection() {
        var reservedInteraction = defaultBookInteractionBuilder().userEmail("::email::").status(RentalStatus.RESERVED).build();
        var returnedInteraction = defaultBookInteractionBuilder().userEmail("::email::").status(RentalStatus.RETURNED).build();
        var rentedInteraction = defaultBookInteraction();

        var bookInteractions = of(
                reservedInteraction,
                rentedInteraction,
                returnedInteraction
        ).collect(toList());;

        var bookRecordDtoCollection = bookRecordMapper.toDtoCollection(bookInteractions);

        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(bookRecordDtoCollection)
                    .extracting("email", "status")
                    .containsExactlyInAnyOrder(
                            tuple(reservedInteraction.getUserEmail(), RentalStatus.RESERVED),
                            tuple(returnedInteraction.getUserEmail(), RentalStatus.RETURNED),
                            tuple(rentedInteraction.getUserEmail(), RentalStatus.RENTED)
                    );
        }
    }
}
