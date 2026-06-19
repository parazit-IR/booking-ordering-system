package com.hooshmand.shipping.service.domain.dto.create;


import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateBookingResponse {
	@NotNull
	private final UUID bookingTrackingId;
	@NotNull
	private final BookingStatus bookingStatus;
	@NotNull
	private final String message;
}
