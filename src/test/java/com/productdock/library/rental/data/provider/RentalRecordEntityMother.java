package com.productdock.library.rental.data.provider;


import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.service.RentalRecordEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.BookInteractionMother.defaultBookInteraction;

public class RentalRecordEntityMother {

    private static final String defaultId = "1";
    private static final String defaultBookId = "1";

    private static final List<BookInteraction> defaultInteractions = new ArrayList<>
            (Arrays.asList(defaultBookInteraction()));

    public static RentalRecordEntity defaultRentalRecordEntity() {
        return RentalRecordEntity.builder()
                .id(defaultId)
                .bookId(defaultBookId)
                .interactions(defaultInteractions)
                .build();
    }
}
