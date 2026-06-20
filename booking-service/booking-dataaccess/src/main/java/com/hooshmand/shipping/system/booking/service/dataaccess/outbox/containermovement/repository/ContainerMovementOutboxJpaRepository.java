package com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.repository;


import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.entity.ContainerMovementOutboxEntity;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContainerMovementOutboxJpaRepository extends JpaRepository<ContainerMovementOutboxEntity, UUID> {
	Optional<List<ContainerMovementOutboxEntity>> findByTypeAndOutboxStatusAndSagaStatusIn(String sagaType, OutboxStatus outboxStatus, List<SagaStatus> list);
}
