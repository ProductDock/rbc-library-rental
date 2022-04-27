package com.productdock.library.rental.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.domain.ActivityFactory;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.producer.Publisher;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public record RentalRecordService(RentalRecordMapper recordMapper, Publisher publisher,
                                  RentalRecordRepository rentalRecordRepository,
                                  BookRentalRecordMapper bookRentalRecordMapper,
                                  BookInteractionMapper bookInteractionMapper) {

    public void saveRecordEntity(RentalRecordEntity rentalRecordEntity) {
        rentalRecordRepository.save(rentalRecordEntity);
    }

    public void create(RentalRecordDto rentalRecordDTO, String authToken) {
        BookRentalRecord bookRentalRecord = createBookRentalRecord(rentalRecordDTO.bookId);
        var activity = ActivityFactory.create(rentalRecordDTO.bookStatus, getUserEmailFromToken(authToken));
        bookRentalRecord.trackActivity(activity);
        RentalRecordEntity entity = toEntity(bookRentalRecord);
        rentalRecordRepository.save(entity);
        //publisher.sendMessage(rentalRecordEntity);
    }

    private RentalRecordEntity toEntity(BookRentalRecord bookRentalRecord) {
        List<BookInteraction> reservations = bookRentalRecord.getReservations().stream().map(bookInteractionMapper::toEntity).collect(Collectors.toList());
        List<BookInteraction> rents = bookRentalRecord.getBorrows().stream().map(bookInteractionMapper::toEntity).collect(Collectors.toList());
        RentalRecordEntity rentalRecordEntity = new RentalRecordEntity(bookRentalRecord.getBookId(), reservations, rents);
        return rentalRecordEntity;
    }

    private BookRentalRecord createBookRentalRecord(String bookId) {
        Optional<RentalRecordEntity> recordEntity = rentalRecordRepository.findById(bookId);
        if (recordEntity.isEmpty()) {
            return new BookRentalRecord(bookId);
        } else {
            List<BookRentalRecord.BookCopy> bookCopies = from(recordEntity.get());
            return new BookRentalRecord(bookId, bookCopies);
        }
    }

    private List<BookRentalRecord.BookCopy> from(RentalRecordEntity recordEntity) {
        return Stream.concat(
                recordEntity.getReservations().stream()
                        .map(r -> new BookRentalRecord.BookCopy(r.getUserEmail(), RentalStatus.RESERVE)),
                recordEntity.getRents().stream()
                        .map(r -> new BookRentalRecord.BookCopy(r.getUserEmail(), RentalStatus.RENT)))
                .collect(Collectors.toList());
    }

    private String getUserEmailFromToken(String authToken) {
        String[] chunks = authToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
        JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
        return (String) jsonObject.get("email");
    }

}
