package com.hooshmand.shipping.service.domain;


import com.hooshmand.shipping.booking.service.domain.event.BookingCreatedEvent;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingCommand;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingResponse;
import com.hooshmand.shipping.service.domain.mapper.BookingDataMapper;
import com.hooshmand.shipping.service.domain.outbox.scheduler.containermovement.ContainerMovementOutboxHelper;
import com.hooshmand.shipping.service.domain.ports.BookingSagaHelper;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BookingCreateCommandHandler {

	private final BookingCreateHelper bookingCreateHelper;
	private final BookingDataMapper bookingDataMapper;
	private final ContainerMovementOutboxHelper containerMovementOutboxHelper;
	private final BookingSagaHelper bookingSagaHelper;


	public BookingCreateCommandHandler(BookingCreateHelper bookingCreateHelper, BookingDataMapper bookingDataMapper, ContainerMovementOutboxHelper containerMovementOutboxHelper, BookingSagaHelper bookingSagaHelper) {
		this.bookingCreateHelper = bookingCreateHelper;
		this.bookingDataMapper = bookingDataMapper;
		this.containerMovementOutboxHelper = containerMovementOutboxHelper;
		this.bookingSagaHelper = bookingSagaHelper;
	}

	public CreateBookingResponse createBooking(CreateBookingCommand createBookingCommand) {
		BookingCreatedEvent bookingCreatedEvent = bookingCreateHelper.persistBooking(createBookingCommand);
		log.info("Booking is created with id: {}", bookingCreatedEvent.getBooking().getId().getValue());
		CreateBookingResponse createBookingResponse = bookingDataMapper.bookingToCreateBookingResponse(bookingCreatedEvent.getBooking(),
				"Booking created successfully");


		containerMovementOutboxHelper.saveContainerMovementOutboxMessage(bookingDataMapper
						.bookingCreatedEventToBookingContainerMovementEventPayload(bookingCreatedEvent),
				bookingCreatedEvent.getBooking().getStatus(),
				bookingSagaHelper.bookingStatusToSagaStatus(bookingCreatedEvent.getBooking().getStatus()),
				OutboxStatus.STARTED,
				UUID.randomUUID());

		log.info("Returning CreateBookingResponse with order id: {}", bookingCreatedEvent.getBooking().getId());

		return createBookingResponse;
	}
}
