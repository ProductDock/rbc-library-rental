package com.productdock.library.rental.adapter.in.scheduled;

import com.productdock.library.rental.application.port.in.CancelExpiredReservationsUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Component
public class CancelExpiredReservationsJob {

    private CancelExpiredReservationsUseCase cancelExpiredReservationsUseCase;

    @Scheduled(cron = "${reservations.auto-canceling.scheduled}")
    public void cancelReservations() {
        log.debug("Started scheduled task for canceling expired reservations, on date: {}", new Date());
        cancelExpiredReservationsUseCase.cancelExpiredReservations();
    }
}
