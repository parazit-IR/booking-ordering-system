package com.hooshmand.shipping.system.booking.service.application.rest;


import com.hooshmand.shipping.service.domain.dto.create.CreateBookingCommand;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingResponse;
import com.hooshmand.shipping.service.domain.ports.input.service.BookingApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bookings", produces = "application/vnd.api.v1+json")
public class BookingController {
	private final BookingApplicationService bookingApplicationService;

	public BookingController(BookingApplicationService bookingApplicationService) {
		this.bookingApplicationService = bookingApplicationService;
	}

	@PostMapping
	public ResponseEntity<CreateBookingResponse> createOrder(@RequestBody CreateBookingCommand createBookingCommand) {
		log.info("Creating booking by items: [{}]", createBookingCommand.getItems());
		CreateBookingResponse createBookingResponse = bookingApplicationService.createBooking(createBookingCommand);
		log.info("Order created with tracking id: {}", createBookingResponse.getBookingTrackingId());
		return ResponseEntity.ok(createBookingResponse);
	}
}
