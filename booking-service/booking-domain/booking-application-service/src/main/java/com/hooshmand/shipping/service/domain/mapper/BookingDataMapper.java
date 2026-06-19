package com.hooshmand.shipping.service.domain.mapper;


import com.hooshmand.shipping.booking.service.domain.entity.Booking;
import com.hooshmand.shipping.booking.service.domain.entity.BookingItem;
import com.hooshmand.shipping.booking.service.domain.event.BookingCreatedEvent;
import com.hooshmand.shipping.booking.service.domain.valueobject.TrackingId;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingCommand;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingResponse;
import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementEventPayload;
import com.hooshmand.shipping.system.domain.valuobject.ContainerMovementBookingStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingDataMapper {


	public Booking createBookingCommandToBooking(CreateBookingCommand createBookingCommand) {
		return Booking.builder()
				.items(bookingItemsToBookingItemEntities(createBookingCommand.getItems()))
				.build();
	}

	private List<BookingItem> bookingItemsToBookingItemEntities(List<com.hooshmand.shipping.service.domain.dto.create.BookingItem> items) {
		return items.stream()
				.map(item -> BookingItem.builder()
						.quantity(item.getQuantity())
						.build())
				.collect(Collectors.toList());
	}


	public CreateBookingResponse bookingToCreateBookingResponse(Booking booking, String message) {
		return CreateBookingResponse.builder()
				.bookingStatus(booking.getStatus())
				.bookingTrackingId(booking.getTrackingId().getValue())
				.message(message)
				.build();
	}

	public BookingContainerMovementEventPayload bookingCreatedEventToBookingContainerMovementEventPayload(BookingCreatedEvent bookingCreatedEvent) {
		return BookingContainerMovementEventPayload.builder()
				.bookingId(bookingCreatedEvent.getBooking().getId().getValue().toString())
				.createdAt(bookingCreatedEvent.getCreatedAt())
				.containerMovementBookingStatus(ContainerMovementBookingStatus.PENDING.name())
				.build();
	}

}
