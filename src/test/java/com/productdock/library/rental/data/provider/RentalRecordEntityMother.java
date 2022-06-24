package com.productdock.library.rental.data.provider;


import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.BookInteractionMother.defaultBookInteraction;

public class RentalRecordEntityMother {

    private static final String defaultId = "2";
    private static final String defaultBookId = "1";

    private static final List<BookCopyRentalState> defaultInteractions = new ArrayList<>
            (Arrays.asList(defaultBookInteraction()));

    public static BookRentalState defaultRentalRecordEntity() {
        return defaultRentalRecordEntityBuilder().build();
    }

    public static BookRentalState.BookRentalStateBuilder defaultRentalRecordEntityBuilder() {
        return BookRentalState.builder()
                .id(defaultId)
                .bookId(defaultBookId)
                .bookCopiesRentalState(defaultInteractions);
    }
}
