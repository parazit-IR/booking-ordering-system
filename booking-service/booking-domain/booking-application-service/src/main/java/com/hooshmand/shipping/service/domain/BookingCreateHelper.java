package com.hooshmand.shipping.service.domain;


import com.hooshmand.shipping.booking.service.domain.BookingDomainService;
import com.hooshmand.shipping.booking.service.domain.entity.Booking;
import com.hooshmand.shipping.booking.service.domain.event.BookingCreatedEvent;
import com.hooshmand.shipping.booking.service.domain.exception.BookingDomainException;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingCommand;
import com.hooshmand.shipping.service.domain.mapper.BookingDataMapper;
import com.hooshmand.shipping.service.domain.ports.output.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class BookingCreateHelper {

	private final BookingDataMapper bookingDataMapper;
	private final BookingDomainService bookingDomainService;
	private final BookingRepository bookingRepository;

	public BookingCreateHelper(BookingDataMapper bookingDataMapper, BookingDomainService bookingDomainService, BookingRepository bookingRepository) {
		this.bookingDataMapper = bookingDataMapper;
		this.bookingDomainService = bookingDomainService;
		this.bookingRepository = bookingRepository;
	}

	@Transactional
	public BookingCreatedEvent persistBooking(CreateBookingCommand createBookingCommand) {
		Booking booking = bookingDataMapper.createBookingCommandToBooking(createBookingCommand);
		BookingCreatedEvent bookingCreatedEvent = bookingDomainService.validateAndInitiateBooking(booking);
		saveBooking(booking);
		log.info("Booking is created with id: {}", bookingCreatedEvent.getBooking().getId().getValue());
		return bookingCreatedEvent;
	}

	private Booking saveBooking(Booking booking) {
		Booking bookingResult = bookingRepository.saveBooking(booking);
		if (bookingResult == null) {
			log.error("Could not save booking!");
			throw new BookingDomainException("Could not save booking!");
		}
		log.info("Booking is saved with id: {}", bookingResult.getId().getValue());
		return bookingResult;
	}
}
