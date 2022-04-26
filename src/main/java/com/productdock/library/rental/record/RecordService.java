package com.productdock.library.rental.record;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.productdock.library.rental.domain.BookRentalActionFactory;
import com.productdock.library.rental.domain.BookRentalRecords;
import com.productdock.library.rental.producer.Publisher;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public record RecordService(RecordMapper recordMapper, Publisher publisher, RecordRepository recordRepository, BookRentalRecordsCreator bookRentalRecordsCreator) {
    public void saveRecordEntity(RecordEntity recordEntity) {
        recordRepository.save(recordEntity);
    }

    public void create(RecordDto rentalRecordDto, String authToken) {
        var bookRentalRecords = makeBookRentalRecords(rentalRecordDto);

        var userEmail = getUserEmailFromToken(authToken);
        var rentalAction = BookRentalActionFactory.create(rentalRecordDto.bookStatus, userEmail);
        bookRentalRecords.applyAction(rentalAction);

        //construct entity again, logic required here
        var freshRecordEntity = new RecordEntity();
        recordRepository.save(freshRecordEntity);

        //from DTO
        //publisher.sendMessage(newRentalEvent);
    }

    private BookRentalRecords makeBookRentalRecords(RecordDto rentalRecordDto) {
        var recordEntity = recordRepository.findById(rentalRecordDto.bookId);
        return bookRentalRecordsCreator.makeFrom(rentalRecordDto, recordEntity);
    }

    private String getUserEmailFromToken(String authToken) {
        String[] chunks = authToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
        JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
        return (String) jsonObject.get("email");
    }

}
