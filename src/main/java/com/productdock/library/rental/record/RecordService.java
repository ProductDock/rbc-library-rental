package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.Publisher;
import org.springframework.stereotype.Service;

@Service
public record RecordService(RecordMapper recordMapper, Publisher publisher, RecordRepository recordRepository) {

    public void saveRecordEntity(RecordEntity recordEntity) {
        recordRepository.save(recordEntity);
        System.out.println("Saved an updated record");
    }

    public void create(RecordDTO recordDTO) {
        RecordEntity recordEntity;
        if(recordRepository.findById(recordDTO.bookId).isEmpty()) {
            recordEntity = createIfRecordNotExist(recordDTO);
        } else {
            recordEntity = recordRepository.findById(recordDTO.bookId).get();
        }
        addBookInteraction(recordDTO.bookStatus, recordEntity);
        recordRepository.save(recordEntity);
        publisher.sendMessage(recordEntity);
    }

    private RecordEntity createIfRecordNotExist(RecordDTO recordDTO) {
        var recordEntity = recordMapper.toEntity(recordDTO);
        return recordEntity;
    }
    private void addBookInteraction(String bookStatus, RecordEntity recordEntity){
        if(bookStatus.equals("RENT")) {
            recordEntity.rentBook("sample@gmail.com");
        } else if(bookStatus.equals("RESERVE")) {
            recordEntity.reserveBook("sample@gmail.com");
        } else {
            recordEntity.returnBook("sample@gmail.com");
        }
    }
}
