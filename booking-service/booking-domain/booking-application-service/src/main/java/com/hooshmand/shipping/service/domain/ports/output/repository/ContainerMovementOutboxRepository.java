package com.hooshmand.shipping.service.domain.ports.output.repository;

import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;

public interface ContainerMovementOutboxRepository {
	BookingContainerMovementOutboxMessage save(BookingContainerMovementOutboxMessage bookingContainerMovementOutboxMessage);

}
