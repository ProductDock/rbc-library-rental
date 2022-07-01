package com.productdock.library.rental.config.dbmigrations;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.productdock.library.rental.adapter.out.mongo.BookRentalStateRepository;
import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;
import com.productdock.library.rental.config.coverage.Generated;
import com.productdock.library.rental.domain.RentalStatus;

import java.util.Calendar;

@Generated
@ChangeLog(order = "001")
public class DatabaseInitChangeLog {

    @ChangeSet(order = "001", id = "init_rental_records", author = "pd")
    public void initRentalRecords(BookRentalStateRepository bookRentalStateRepository) {
        var calendar = Calendar.getInstance();

        calendar.set(2019, Calendar.OCTOBER, 29);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("2")
                .bookCopyRentalState(BookCopyRentalState.builder()
                            .userEmail("adriana.martinovic@productdock.com")
                            .status(RentalStatus.RENTED)
                            .date(calendar.getTime())
                            .build())
                .build());

        calendar.set(2021, Calendar.DECEMBER, 15);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("4")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("jovanka.bobic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.AUGUST, 5);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("6")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("jovana.minic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 14);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("7")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("marko.radinovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 14);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("30")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("nemanja.vasiljevic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2020, Calendar.OCTOBER, 16);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("36")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("jovanka.bobic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2020, Calendar.MARCH, 29);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("38")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("dragana.bogdanovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2020, Calendar.MARCH, 29);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("40")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("dragana.bogdanovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.MAY, 24);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("52")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("bojan.ristic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.NOVEMBER, 1);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("55")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("nikola.lajic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.SEPTEMBER, 15);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("57")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("suzana.bogojevic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.AUGUST, 25);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("60")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("jovana.minic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.NOVEMBER, 5);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("63")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("andrija.vujasinovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 4);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("65")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("adriana.martinovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.JANUARY, 12);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("66")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("bojana.armacki@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.JANUARY, 19);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("67")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("nemanja.vasiljevic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 2);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("69")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("milica.zivkov@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 2);
        bookRentalStateRepository.save(BookRentalState.builder()
                .bookId("70")
                .bookCopyRentalState(BookCopyRentalState.builder()
                        .userEmail("jaroslav.slivka@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());
    }
}
