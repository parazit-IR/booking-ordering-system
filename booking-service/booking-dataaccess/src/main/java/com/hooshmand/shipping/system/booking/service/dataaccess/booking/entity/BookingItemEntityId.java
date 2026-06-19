package com.hooshmand.shipping.system.booking.service.dataaccess.booking.entity;


import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingItemEntityId implements Serializable {
	private Long id;
	private BookingEntity booking;

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		BookingItemEntityId that = (BookingItemEntityId) object;
		return Objects.equals(id, that.id) && Objects.equals(booking, that.booking);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, booking);
	}
}
