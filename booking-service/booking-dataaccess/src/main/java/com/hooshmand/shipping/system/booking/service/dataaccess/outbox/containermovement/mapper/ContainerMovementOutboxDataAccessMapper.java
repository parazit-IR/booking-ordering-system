package com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.mapper;

import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.entity.ContainerMovementOutboxEntity;
import org.springframework.stereotype.Component;

@Component
public class ContainerMovementOutboxDataAccessMapper {
	public ContainerMovementOutboxEntity bookingContainerMovementOutboxMessageToOutboxEntity(BookingContainerMovementOutboxMessage bookingContainerMovementOutboxMessage) {
		return ContainerMovementOutboxEntity.builder()
				.id(bookingContainerMovementOutboxMessage.getId())
				.sagaId(bookingContainerMovementOutboxMessage.getSagaId())
				.createdAt(bookingContainerMovementOutboxMessage.getCreatedAt())
				.type(bookingContainerMovementOutboxMessage.getType())
				.payload(bookingContainerMovementOutboxMessage.getPayload())
				.bookingStatus(bookingContainerMovementOutboxMessage.getBookingStatus())
				.sagaStatus(bookingContainerMovementOutboxMessage.getSagaStatus())
				.outboxStatus(bookingContainerMovementOutboxMessage.getOutboxStatus())
				.version(bookingContainerMovementOutboxMessage.getVersion())
				.build();
	}

	public BookingContainerMovementOutboxMessage containerMovementOutboxEntityToBookingContainerMovementOutboxMessage(ContainerMovementOutboxEntity containerMovementOutboxEntity) {
		return BookingContainerMovementOutboxMessage.builder()
				.id(containerMovementOutboxEntity.getId())
				.sagaId(containerMovementOutboxEntity.getSagaId())
				.createdAt(containerMovementOutboxEntity.getCreatedAt())
				.type(containerMovementOutboxEntity.getType())
				.payload(containerMovementOutboxEntity.getPayload())
				.bookingStatus(containerMovementOutboxEntity.getBookingStatus())
				.sagaStatus(containerMovementOutboxEntity.getSagaStatus())
				.outboxStatus(containerMovementOutboxEntity.getOutboxStatus())
				.version(containerMovementOutboxEntity.getVersion())
				.build();
	}
}
