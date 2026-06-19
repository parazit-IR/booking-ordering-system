package com.hooshmand.shipping.service.domain.outbox.model.containermovement;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BookingContainerMovementEventPayload {
	@JsonProperty
	private String bookingId;
	@JsonProperty
	private ZonedDateTime createdAt;
	@JsonProperty
	private String containerMovementBookingStatus;
}
