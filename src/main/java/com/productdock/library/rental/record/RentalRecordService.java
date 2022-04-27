package com.productdock.library.rental.record;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.productdock.library.rental.producer.Publisher;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public record RentalRecordService(RentalRecordMapper recordMapper, Publisher publisher, RentalRecordRepository rentalRecordRepository) {
    public void saveRecordEntity(RentalRecordEntity rentalRecordEntity) {
        rentalRecordRepository.save(rentalRecordEntity);
    }

    public void create(RentalRecordDto rentalRecordDTO, String authToken) {
        RentalRecordEntity rentalRecordEntity = getRecordEntity(rentalRecordDTO);
        addBookInteraction(rentalRecordDTO.bookStatus, rentalRecordEntity, getUserEmailFromToken(authToken));
        rentalRecordRepository.save(rentalRecordEntity);
        publisher.sendMessage(rentalRecordEntity);
    }

    private RentalRecordEntity getRecordEntity(RentalRecordDto rentalRecordDTO) {
        Optional<RentalRecordEntity> recordEntity = rentalRecordRepository.findById(rentalRecordDTO.bookId);
        if (recordEntity.isEmpty()) {
            return recordMapper.toEntity(rentalRecordDTO);
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

    private void addBookInteraction(RentalStatus bookStatus, RentalRecordEntity rentalRecordEntity, String userEmail) {
        if (bookStatus.equals("RENT")) {
            rentalRecordEntity.rentBook(userEmail);
        } else if (bookStatus.equals("RESERVE")) {
            rentalRecordEntity.reserveBook(userEmail);
        } else {
            rentalRecordEntity.returnBook(userEmail);
        }
    }
}
