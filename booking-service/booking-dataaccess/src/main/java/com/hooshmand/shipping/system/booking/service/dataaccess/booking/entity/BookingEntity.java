package com.hooshmand.shipping.system.booking.service.dataaccess.booking.entity;


import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
@Entity
public class BookingEntity {
	@Id
	private UUID id;
	private UUID trackingId;
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	private String failureMessages;

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<bookingItemEntity> items;

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		BookingEntity that = (BookingEntity) object;
		return Objects.equals(id, that.id) && Objects.equals(trackingId, that.trackingId) && bookingStatus == that.bookingStatus && Objects.equals(failureMessages, that.failureMessages) && Objects.equals(items, that.items);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, trackingId, bookingStatus, failureMessages, items);
	}
}
