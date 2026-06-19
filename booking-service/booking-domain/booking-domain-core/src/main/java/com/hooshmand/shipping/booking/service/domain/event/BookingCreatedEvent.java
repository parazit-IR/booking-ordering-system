package com.hooshmand.shipping.booking.service.domain.event;

import com.hooshmand.shipping.booking.service.domain.entity.Booking;

import java.time.ZonedDateTime;

public class BookingCreatedEvent extends BookingEvent {
	public BookingCreatedEvent(Booking booking, ZonedDateTime createdAt) {
		super(booking, createdAt);
	}
}
