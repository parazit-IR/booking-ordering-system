package com.hooshmand.shipping.booking.service.domain;

import com.hooshmand.shipping.booking.service.domain.entity.Booking;
import com.hooshmand.shipping.booking.service.domain.event.BookingCreatedEvent;
import com.hooshmand.shipping.system.domain.DomainConstants;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.hooshmand.shipping.system.domain.DomainConstants.UTC;

@Slf4j
public class BookingDomainServiceImpl implements BookingDomainService{

	@Override
	public BookingCreatedEvent validateAndInitiateBooking(Booking booking) {
		booking.validateBooking();
		booking.initializeBooking();
		log.info("Booking with id: {} is initiated", booking.getId().getValue());
		return new BookingCreatedEvent(booking, ZonedDateTime.now(ZoneId.of(UTC)));
	}
}
