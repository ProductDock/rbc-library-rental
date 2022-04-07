package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.Publisher;
import com.productdock.library.rental.producer.messages.Notification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record RecordService(RecordMapper recordMapper, Publisher publisher, RecordRepository recordRepository) {

    public void create(RecordDTO recordDTO) {
        RecordEntity recordEntity = recordMapper.toEntity(recordDTO);
        recordEntity.setId(UUID.randomUUID().toString());
        recordRepository.save(recordEntity);
        publisher.sendMessage(new Notification(recordEntity.getUserEmail(),
                "Book with id: " + recordDTO.bookId + " changed status to: " + recordDTO.bookStatus));

    }
}
