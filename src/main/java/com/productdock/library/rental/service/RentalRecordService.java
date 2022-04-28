package com.productdock.library.rental.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.kafka.RentalsPublisher;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

import static com.productdock.library.rental.domain.UserActivityFactory.createUserActivity;

@Service
public record RentalRecordService(RentalRecordMapper recordMapper, RentalsPublisher publisher,
                                  RentalRecordRepository rentalRecordRepository,
                                  BookRentalRecordMapper bookRentalRecordMapper,
                                  BookCopyMapper bookInteractionMapper, RentalRecordsMessageMapper rentalRecordsMessageMapper) {

    public void create(RentalRequest rentalRequest, String authToken) throws Exception {
        BookRentalRecord bookRentalRecord = createBookRentalRecord(rentalRequest.bookId);

        var activity = createUserActivity(rentalRequest.requestedStatus, getUserEmailFromToken(authToken));
        bookRentalRecord.trackActivity(activity);

        saveRentalRecord(bookRentalRecord);

        var rentalRecordsMessage = rentalRecordsMessageMapper.toMessage(bookRentalRecord);
        publisher.sendMessage(rentalRecordsMessage);
    }

    private BookRentalRecord createBookRentalRecord(String bookId) {
        Optional<RentalRecordEntity> recordEntity = rentalRecordRepository.findById(bookId);
        if (recordEntity.isEmpty()) {
            return new BookRentalRecord(bookId);
        } else {
            return bookRentalRecordMapper.toDomain(recordEntity.get());
        }
    }

    private void saveRentalRecord(BookRentalRecord bookRentalRecord) {
        RentalRecordEntity entity = bookRentalRecordMapper.toEntity(bookRentalRecord);
        rentalRecordRepository.save(entity);
    }

    private String getUserEmailFromToken(String authToken) {
        String[] chunks = authToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
        JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
        return (String) jsonObject.get("email");
    }

}
