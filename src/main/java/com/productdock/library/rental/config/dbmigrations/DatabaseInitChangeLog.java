package com.productdock.library.rental.config.dbmigrations;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordRepository;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Calendar;

@ChangeLog(order = "001")
public class DatabaseInitChangeLog {

    @ChangeSet(order = "001", id = "init_rental_records", author = "pd")
    public void initRentalRecords(RentalRecordRepository rentalRecordRepository) {
        var calendar = Calendar.getInstance();

        calendar.set(2019, Calendar.OCTOBER, 29);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("2")
                .interaction(BookInteraction.builder()
                            .userEmail("adriana.martinovic@productdock.com")
                            .status(RentalStatus.RENTED)
                            .date(calendar.getTime())
                            .build())
                .build());

        calendar.set(2021, Calendar.DECEMBER, 15);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("4")
                .interaction(BookInteraction.builder()
                        .userEmail("jovanka.bobic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.AUGUST, 5);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("6")
                .interaction(BookInteraction.builder()
                        .userEmail("jovana.minic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 14);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("7")
                .interaction(BookInteraction.builder()
                        .userEmail("marko.radinovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 14);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("30")
                .interaction(BookInteraction.builder()
                        .userEmail("nemanja.vasiljevic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2020, Calendar.OCTOBER, 16);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("36")
                .interaction(BookInteraction.builder()
                        .userEmail("jovanka.bobic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2020, Calendar.MARCH, 29);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("38")
                .interaction(BookInteraction.builder()
                        .userEmail("dragana.bogdanovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2020, Calendar.MARCH, 29);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("40")
                .interaction(BookInteraction.builder()
                        .userEmail("dragana.bogdanovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.MAY, 24);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("52")
                .interaction(BookInteraction.builder()
                        .userEmail("bojan.ristic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.NOVEMBER, 1);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("55")
                .interaction(BookInteraction.builder()
                        .userEmail("nikola.lajic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.SEPTEMBER, 15);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("57")
                .interaction(BookInteraction.builder()
                        .userEmail("suzana.bogojevic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.AUGUST, 25);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("60")
                .interaction(BookInteraction.builder()
                        .userEmail("jovana.minic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2021, Calendar.NOVEMBER, 5);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("63")
                .interaction(BookInteraction.builder()
                        .userEmail("andrija.vujasinovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 4);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("65")
                .interaction(BookInteraction.builder()
                        .userEmail("adriana.martinovic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.JANUARY, 12);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("66")
                .interaction(BookInteraction.builder()
                        .userEmail("bojana.armacki@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.JANUARY, 19);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("67")
                .interaction(BookInteraction.builder()
                        .userEmail("nemanja.vasiljevic@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 2);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("69")
                .interaction(BookInteraction.builder()
                        .userEmail("milica.zivkov@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());

        calendar.set(2022, Calendar.MARCH, 2);
        rentalRecordRepository.save(RentalRecordEntity.builder()
                .bookId("70")
                .interaction(BookInteraction.builder()
                        .userEmail("jaroslav.slivka@productdock.com")
                        .status(RentalStatus.RENTED)
                        .date(calendar.getTime())
                        .build())
                .build());
    }
}
