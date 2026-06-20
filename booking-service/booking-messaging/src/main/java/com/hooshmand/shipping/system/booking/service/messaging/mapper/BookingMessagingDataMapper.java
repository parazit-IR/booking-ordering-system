package com.hooshmand.shipping.system.booking.service.messaging.mapper;


import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementEventPayload;
import com.hooshmand.shipping.system.kafka.booking.avro.model.ContainerMovementBookingStatus;
import com.hooshmand.shipping.system.kafka.booking.avro.model.ContainerMovementRequestAvroModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingMessagingDataMapper {

	public ContainerMovementRequestAvroModel bookingContainerMovementEventToContainerMovementRequestAvroModel(String sagaId, BookingContainerMovementEventPayload bookingContainerMovementEventPayload) {
		return ContainerMovementRequestAvroModel.newBuilder()
				.setId(UUID.randomUUID().toString())
				.setSagaId(sagaId)
				.setBookingId(bookingContainerMovementEventPayload.getBookingId())
				.setCreatedAt(bookingContainerMovementEventPayload.getCreatedAt().toInstant())
				.setContainerMovementBookingStatus(ContainerMovementBookingStatus.valueOf(bookingContainerMovementEventPayload.getContainerMovementBookingStatus()))
				.build();
	}
}
