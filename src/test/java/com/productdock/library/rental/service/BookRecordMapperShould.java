package com.productdock.library.rental.service;

import com.productdock.library.rental.book.BookInteraction;
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
class BookRecordMapperShould {

    public static final BookInteraction RESERVED_INTERACTION = defaultBookInteractionBuilder().status(RentalStatus.RESERVED).build();
    public static final BookInteraction RETURNED_INTERACTION = defaultBookInteractionBuilder().status(RentalStatus.RETURNED).build();
    public static final BookInteraction RENTED_INTERACTION = defaultBookInteraction();

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
        var bookInteractions = of(
                RESERVED_INTERACTION,
                RENTED_INTERACTION,
                RETURNED_INTERACTION
        ).collect(toList());

        var bookRecordDtoCollection = bookRecordMapper.toDtoCollection(bookInteractions);

        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(bookRecordDtoCollection)
                    .extracting("email", "status")
                    .containsExactlyInAnyOrder(
                            tuple(RESERVED_INTERACTION.getUserEmail(), RentalStatus.RESERVED),
                            tuple(RETURNED_INTERACTION.getUserEmail(), RentalStatus.RETURNED),
                            tuple(RENTED_INTERACTION.getUserEmail(), RentalStatus.RENTED)
                    );
        }
    }
}
