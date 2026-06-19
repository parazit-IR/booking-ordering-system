package com.hooshmand.shipping.system.booking.service.dataaccess.booking.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BookingItemEntityId.class)
@Table(name = "booking_items")
@Entity
public class bookingItemEntity {
	@Id
	private Long id;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BOOKING_ID")
	private BookingEntity booking;

	private Integer quantity;


	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		bookingItemEntity that = (bookingItemEntity) object;
		return Objects.equals(id, that.id) && Objects.equals(booking, that.booking) && Objects.equals(quantity, that.quantity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, booking, quantity);
	}
}
