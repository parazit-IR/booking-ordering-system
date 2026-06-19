package com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.repository;


import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.entity.ContainerMovementOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContainerMovementOutboxJpaRepository extends JpaRepository<ContainerMovementOutboxEntity, UUID> {
}
