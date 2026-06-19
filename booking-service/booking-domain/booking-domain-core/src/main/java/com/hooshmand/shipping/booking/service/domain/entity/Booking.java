package com.hooshmand.shipping.booking.service.domain.entity;

import com.hooshmand.shipping.booking.service.domain.valueobject.TrackingId;
import com.hooshmand.shipping.system.domain.entity.AggregateRoot;
import com.hooshmand.shipping.system.domain.valuobject.BookingId;
import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;

import java.util.List;
import java.util.UUID;

public class Booking extends AggregateRoot<BookingId> {

	private final List<BookingItem> items;
	private final BookingStatus status;
	private final TrackingId trackingId;

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
		trackingId = new TrackingId(UUID.randomUUID());
		status = BookingStatus.PENDING;
		//business logic
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
			trackingId = trackingId;
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
