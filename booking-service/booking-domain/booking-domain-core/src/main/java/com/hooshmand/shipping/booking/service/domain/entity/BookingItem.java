package com.hooshmand.shipping.booking.service.domain.entity;

import com.hooshmand.shipping.booking.service.domain.valueobject.BookingItemId;
import com.hooshmand.shipping.system.domain.entity.BaseEntity;
import com.hooshmand.shipping.system.domain.valuobject.BookingId;

public class BookingItem extends BaseEntity<BookingItemId> {
	private BookingId bookingId;
	private final int quantity;

	void initializeBookingItem(BookingId bookingId, BookingItemId bookingItemId) {
		this.bookingId = bookingId;
		super.setId(bookingItemId);
	}

	public BookingItem(Builder builder) {
		super.setId(builder.bookingItemId);
		this.quantity = builder.quantity;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private BookingItemId bookingItemId;
		private int quantity;

		private Builder() {
		}

		public Builder bookingItemId(BookingItemId val) {
			bookingItemId = val;
			return this;
		}

		public Builder quantity(int val) {
			quantity = val;
			return this;
		}

		public BookingItem build() {
			return new BookingItem(this);
		}
	}

	public int getQuantity() {
		return quantity;
	}
}
