package com.hooshmand.shipping.booking.service.domain.entity;

import com.hooshmand.shipping.booking.service.domain.exception.BookingDomainException;
import com.hooshmand.shipping.booking.service.domain.valueobject.BookingItemId;
import com.hooshmand.shipping.booking.service.domain.valueobject.TrackingId;
import com.hooshmand.shipping.system.domain.entity.AggregateRoot;
import com.hooshmand.shipping.system.domain.valuobject.BookingId;
import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;

import java.util.List;
import java.util.UUID;

public class Booking extends AggregateRoot<BookingId> {

	private final List<BookingItem> items;
	private BookingStatus status;
	private TrackingId trackingId;
	private List<String> failureMessages;

	public static final String FAILURE_MESSAGE_DELIMITER = ",";

	private Booking(Builder builder) {
		super.setId(builder.bookingId);
		this.trackingId = builder.trackingId;
		this.items = builder.items;
		this.status = builder.status;
	}

	public void validateBooking() {
		//business logic
	}

	public void initializeBooking() {
		setId(new BookingId(UUID.randomUUID()));
		this.trackingId = new TrackingId(UUID.randomUUID());
		this.status = BookingStatus.PENDING;
		initializeBookingItems();
		//business logic
	}

	private void initializeBookingItems() {
		long itemId = 1;
		for (BookingItem bookingItem: items) {
			bookingItem.initializeBookingItem(super.getId(), new BookingItemId(itemId++));
		}
	}

	public List<String> getFailureMessages() {
		return failureMessages;
	}

	public void initCancel(List<String> failureMessages) {
		if (status != BookingStatus.PAID) {
			throw new BookingDomainException("Booking is not in correct state for initCancel operation!");
		}
		status = BookingStatus.CANCELLING;
		updateFailureMessages(failureMessages);
	}

	private void updateFailureMessages(List<String> failureMessages) {
		if (this.failureMessages != null && failureMessages != null) {
			this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
		}
		if (this.failureMessages == null) {
			this.failureMessages = failureMessages;
		}
	}

	public static final class Builder {
		private BookingId bookingId;
		private BookingStatus status;
		private List<BookingItem> items;
		private TrackingId trackingId;

		private List<String> failureMessages;

		private Builder() {
		}

		public Builder bookingId(BookingId val) {
			bookingId = val;
			return this;
		}

		public Builder trackingId(TrackingId trackingId) {
			this.trackingId = trackingId;
			return this;
		}

		public Builder status(BookingStatus status) {
			this.status = status;
			return this;
		}

		public Builder items(List<BookingItem> items) {
			this.items = items;
			return this;
		}

		public Booking build() {
			return new Booking(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public List<BookingItem> getItems() {
		return items;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public TrackingId getTrackingId() {
		return trackingId;
	}
}
