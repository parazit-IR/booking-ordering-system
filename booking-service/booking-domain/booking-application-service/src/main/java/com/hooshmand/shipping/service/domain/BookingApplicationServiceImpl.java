package com.hooshmand.shipping.service.domain;

import com.hooshmand.shipping.service.domain.dto.create.CreateBookingCommand;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingResponse;
import com.hooshmand.shipping.service.domain.ports.input.service.BookingApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Validated
@Service
public class BookingApplicationServiceImpl implements BookingApplicationService {

	private final BookingCreateCommandHandler bookingCreateCommandHandler;

	public BookingApplicationServiceImpl(BookingCreateCommandHandler bookingCreateCommandHandler) {
		this.bookingCreateCommandHandler = bookingCreateCommandHandler;
	}

	@Override
	public CreateBookingResponse createBooking(CreateBookingCommand createBookingCommand) {
		return bookingCreateCommandHandler.createBooking(createBookingCommand);
	}
}
