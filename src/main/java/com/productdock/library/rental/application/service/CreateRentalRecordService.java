package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.CreateRentalRecordUseCase;
import com.productdock.library.rental.application.port.out.persistence.RentalRecordRepository;
import com.productdock.library.rental.application.port.out.kafka.SendRentalRecordOutPort;
import com.productdock.library.rental.domain.RentalRequestDto;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.domain.UserActivityFactory;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public record CreateRentalRecordService(RentalRecordRepository rentalRecordRepository,
                                        BookRecordMapper bookRecordMapper,
                                        BookRentalRecordMapper bookRentalRecordMapper,
                                        SendRentalRecordOutPort sendRentalRecordOutPort) implements CreateRentalRecordUseCase {

    @SneakyThrows
    public void create(RentalRequestDto rentalRequestDto, String userEmail) {
        var bookRentalRecord = createBookRentalRecord(rentalRequestDto.bookId);

        var activity = UserActivityFactory.createUserActivity(rentalRequestDto.requestedStatus, userEmail);
        bookRentalRecord.trackActivity(activity);

        saveRentalRecord(bookRentalRecord);

        sendRentalRecordOutPort.sendMessage(bookRentalRecord);
    }

    private BookRentalRecord createBookRentalRecord(String bookId) {
        var recordEntity = rentalRecordRepository.findByBookId(bookId);
        if (recordEntity.isEmpty()) {
            return new BookRentalRecord(bookId);
        } else {
            return bookRentalRecordMapper.toDomain(recordEntity.get());
        }
    }

    private void saveRentalRecord(BookRentalRecord bookRentalRecord) {
        var previousRecordEntity = rentalRecordRepository.findByBookId(bookRentalRecord.getBookId());
        var newRecordEntity = bookRentalRecordMapper.toEntity(bookRentalRecord);
        if (previousRecordEntity.isPresent()) {
            newRecordEntity.setId(previousRecordEntity.get().getId());
        }
        rentalRecordRepository.save(newRecordEntity);
    }
}
