package com.hooshmand.shipping.service.domain.outbox.scheduler.containermovement;


import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.service.domain.ports.output.message.publisher.containermovement.ContainerMovementRequestMessagePublisher;
import com.hooshmand.shipping.system.outbox.OutboxScheduler;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ContainerMovementOutboxScheduler implements OutboxScheduler {
	private final ContainerMovementOutboxHelper containerMovementOutboxHelper;
	private final ContainerMovementRequestMessagePublisher containerMovementRequestMessagePublisher;

	public ContainerMovementOutboxScheduler(ContainerMovementOutboxHelper containerMovementOutboxHelper,
	                                        ContainerMovementRequestMessagePublisher containerMovementRequestMessagePublisher) {
		this.containerMovementOutboxHelper = containerMovementOutboxHelper;
		this.containerMovementRequestMessagePublisher = containerMovementRequestMessagePublisher;
	}

	@Override
	@Transactional
	@Scheduled(fixedDelayString = "${booking-service.outbox-scheduler-fixed-rate}",
			initialDelayString = "${booking-service.outbox-scheduler-initial-delay}")
	public void processOutboxMessage() {
		Optional<List<BookingContainerMovementOutboxMessage>> outboxMessagesResponse =
				containerMovementOutboxHelper.getContainerMovementOutboxMessageByOutboxStatusAndSagaStatus(
						OutboxStatus.STARTED,
						SagaStatus.STARTED,
						SagaStatus.COMPENSATING);

		if (outboxMessagesResponse.isPresent() && outboxMessagesResponse.get().size() > 0) {
			List<BookingContainerMovementOutboxMessage> outboxMessages = outboxMessagesResponse.get();
			log.info("Received {} BookingContainerMovementOutboxMessage with ids: {}, sending to message bus!",
					outboxMessages.size(),
					outboxMessages.stream().map(outboxMessage ->
							outboxMessage.getId().toString()).collect(Collectors.joining(",")));
			outboxMessages.forEach(outboxMessage ->
					containerMovementRequestMessagePublisher.publish(outboxMessage, this::updateOutboxStatus));
			log.info("{} BookingContainerMovementOutboxMessage sent to message bus!", outboxMessages.size());
		}
	}

	private void updateOutboxStatus(BookingContainerMovementOutboxMessage bookingContainerMovementOutboxMessage,
	                                OutboxStatus outboxStatus) {
		bookingContainerMovementOutboxMessage.setOutboxStatus(outboxStatus);
		containerMovementOutboxHelper.save(bookingContainerMovementOutboxMessage);
		log.info("BookingContainerMovementOutboxMessage is updated with outbox status: {}", outboxStatus.name());
	}
}
