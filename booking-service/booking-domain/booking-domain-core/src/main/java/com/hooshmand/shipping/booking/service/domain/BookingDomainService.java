package com.hooshmand.shipping.booking.service.domain;

import com.hooshmand.shipping.booking.service.domain.entity.Booking;
import com.hooshmand.shipping.booking.service.domain.event.BookingCreatedEvent;

public interface BookingDomainService {

	BookingCreatedEvent validateAndInitiateBooking(Booking booking);

}
