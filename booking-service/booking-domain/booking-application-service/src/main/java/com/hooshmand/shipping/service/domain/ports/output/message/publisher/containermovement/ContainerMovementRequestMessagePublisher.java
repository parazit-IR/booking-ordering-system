package com.hooshmand.shipping.service.domain.ports.output.message.publisher.containermovement;

import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.system.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface ContainerMovementRequestMessagePublisher {
	void publish(BookingContainerMovementOutboxMessage containerMovementOutboxMessage,
	             BiConsumer<BookingContainerMovementOutboxMessage, OutboxStatus> outboxCallback);
}
