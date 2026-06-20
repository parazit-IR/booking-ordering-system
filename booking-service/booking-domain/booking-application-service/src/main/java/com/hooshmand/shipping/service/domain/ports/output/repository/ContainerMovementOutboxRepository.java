package com.hooshmand.shipping.service.domain.ports.output.repository;

import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;

import java.util.List;
import java.util.Optional;

public interface ContainerMovementOutboxRepository {
	BookingContainerMovementOutboxMessage save(BookingContainerMovementOutboxMessage bookingContainerMovementOutboxMessage);

	Optional<List<BookingContainerMovementOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String bookingSagaName, OutboxStatus outboxStatus, SagaStatus[] sagaStatus);
}
