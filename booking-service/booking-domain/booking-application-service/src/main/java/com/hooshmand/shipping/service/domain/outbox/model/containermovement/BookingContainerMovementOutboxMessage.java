package com.hooshmand.shipping.service.domain.outbox.model.containermovement;


import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class BookingContainerMovementOutboxMessage {
	private UUID id;
	private UUID sagaId;
	private ZonedDateTime createdAt;
	private ZonedDateTime processedAt;
	private String type;
	private String payload;
	private SagaStatus sagaStatus;
	private BookingStatus bookingStatus;
	private OutboxStatus outboxStatus;
	private int version;

	public void setProcessedAt(ZonedDateTime processedAt) {
		this.processedAt = processedAt;
	}

	public void setSagaStatus(SagaStatus sagaStatus) {
		this.sagaStatus = sagaStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public void setOutboxStatus(OutboxStatus outboxStatus) {
		this.outboxStatus = outboxStatus;
	}
}
