package com.productdock.library.rental.record;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.productdock.library.rental.domain.BookAction;
import com.productdock.library.rental.domain.BookActionFactory;
import com.productdock.library.rental.producer.Publisher;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public record RecordService(RecordMapper recordMapper, Publisher publisher, RecordRepository recordRepository) {
    public void saveRecordEntity(RecordEntity recordEntity) {
        recordRepository.save(recordEntity);
    }

    public void create(RecordDto recordDTO, String authToken) {
        RecordEntity recordEntity = getRecordEntity(recordDTO);

        BookAction action = BookActionFactory.create(recordDTO.bookStatus, getUserEmailFromToken(authToken));
        action.apply();


        //addBookInteraction(recordDTO.bookStatus, recordEntity, getUserEmailFromToken(authToken));
        recordRepository.save(recordEntity);
        publisher.sendMessage(recordEntity);
    }

    private RecordEntity getRecordEntity(RecordDto recordDTO) {
        Optional<RecordEntity> recordEntity = recordRepository.findById(recordDTO.bookId);
        if (recordEntity.isEmpty()) {
            return recordMapper.toEntity(recordDTO);
        } else {
            return recordEntity.get();
        }
    }

    private String getUserEmailFromToken(String authToken) {
        String[] chunks = authToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
        JSONObject jsonObject = (JSONObject) JSONValue.parse(payload);
        return (String) jsonObject.get("email");
    }

    //moved to  factory
    private void addBookInteraction(String bookStatus, RecordEntity recordEntity, String userEmail) {
        if (bookStatus.equals("RENT")) {
            recordEntity.rentBook(userEmail);
        } else if (bookStatus.equals("RESERVE")) {
            recordEntity.reserveBook(userEmail);
        } else {
            recordEntity.returnBook(userEmail);
        }
    }
}
