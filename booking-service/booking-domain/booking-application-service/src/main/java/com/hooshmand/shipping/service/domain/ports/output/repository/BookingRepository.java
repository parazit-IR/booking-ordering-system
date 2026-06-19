package com.hooshmand.shipping.service.domain.ports.output.repository;

import com.hooshmand.shipping.booking.service.domain.entity.Booking;

public interface BookingRepository {
	Booking saveBooking(Booking booking);
}
