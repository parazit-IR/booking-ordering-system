package com.hooshmand.shipping.system.booking.service.dataaccess.booking.mapper;


import com.hooshmand.shipping.booking.service.domain.entity.Booking;
import com.hooshmand.shipping.booking.service.domain.entity.BookingItem;
import com.hooshmand.shipping.booking.service.domain.valueobject.BookingItemId;
import com.hooshmand.shipping.system.booking.service.dataaccess.booking.entity.BookingEntity;
import com.hooshmand.shipping.system.booking.service.dataaccess.booking.entity.OrderItemEntity;
import com.hooshmand.shipping.system.domain.valuobject.BookingId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingDataAccessMapper {

	//
	public BookingEntity bookingToBookingEntity(Booking booking) {
		BookingEntity bookingEntity = BookingEntity.builder()
				.id(booking.getId().getValue())
				.items(bookingItemsToBookingItemEntities(booking.getItems()))
				.bookingStatus(booking.getStatus())
//				.failureMessages(booking.getFailureMessages() != null ?
//						String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()) : "")
				.build();
		bookingEntity.getItems().forEach(bookingItemEntity -> bookingItemEntity.setBooking(bookingEntity));

		return bookingEntity;
	}

	private List<OrderItemEntity> bookingItemsToBookingItemEntities(List<BookingItem> items) {
		return items.stream()
				.map(orderItem -> OrderItemEntity.builder()
						.id(orderItem.getId().getValue())
						.quantity(orderItem.getQuantity()).build())
				.collect(Collectors.toList());
	}

	public Booking bookingEntityToBooking(BookingEntity bookingEntity) {
		return Booking.builder()
				.status(bookingEntity.getBookingStatus())
				.bookingId(new BookingId(bookingEntity.getId()))
				.items(bookingItemsEntitiesToBookingItems(bookingEntity.getItems()))
				.build();
	}

	private List<BookingItem> bookingItemsEntitiesToBookingItems(List<OrderItemEntity> items) {
		return items.stream()
				.map(bookingItem -> BookingItem.builder()
						.quantity(bookingItem.getQuantity())
						.bookingItemId(new BookingItemId(bookingItem.getId()))
						.build())
				.collect(Collectors.toList());
	}
}
