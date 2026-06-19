package com.hooshmand.shipping.system.booking.service.dataaccess.booking.adapter;

import com.hooshmand.shipping.booking.service.domain.entity.Booking;
import com.hooshmand.shipping.service.domain.ports.output.repository.BookingRepository;
import com.hooshmand.shipping.system.booking.service.dataaccess.booking.mapper.BookingDataAccessMapper;
import com.hooshmand.shipping.system.booking.service.dataaccess.booking.repository.BookingJpaRepository;

public class BookingRepositoryImpl implements BookingRepository {

	private final BookingJpaRepository bookingJpaRepository;
	private final BookingDataAccessMapper bookingDataAccessMapper;

	public BookingRepositoryImpl(BookingJpaRepository bookingJpaRepository,
								 BookingDataAccessMapper bookingDataAccessMapper) {
		this.bookingJpaRepository = bookingJpaRepository;
		this.bookingDataAccessMapper = bookingDataAccessMapper;
	}

	@Override
	public Booking saveBooking(Booking booking) {
		return bookingDataAccessMapper.bookingEntityToBooking(bookingJpaRepository
				.save(bookingDataAccessMapper.bookingToBookingEntity(booking)));
	}
}
