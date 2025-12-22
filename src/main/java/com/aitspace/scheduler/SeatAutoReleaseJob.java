//package com.aitspace.scheduler;
//
//import com.aitspace.entity.Seat;
//import com.aitspace.entity.SeatBooking;
//import com.aitspace.repository.SeatBookingRepository;
//import com.aitspace.repository.SeatRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class SeatAutoReleaseJob {
//
//    private final SeatBookingRepository bookingRepo;
//    private final SeatRepository seatRepo;
//
//    @Scheduled(fixedRate = 60000)
//    @Transactional
//    public void releaseExpiredSeats() {
//
//        List<SeatBooking> expired =
//                bookingRepo.findExpiredBookings(LocalDateTime.now());
//
//        for (SeatBooking booking : expired) {
//
//            booking.setActive(false);
//            booking.setStatus(SeatBooking.BookingStatus.COMPLETED);
//
//            Seat seat = booking.getSeat();
//            seat.setAvailable(true);
//            seat.setSeatStatus(Seat.SeatStatus.UNALLOCATED);
//
//            bookingRepo.save(booking);
//            seatRepo.save(seat);
//        }
//    }
//}
