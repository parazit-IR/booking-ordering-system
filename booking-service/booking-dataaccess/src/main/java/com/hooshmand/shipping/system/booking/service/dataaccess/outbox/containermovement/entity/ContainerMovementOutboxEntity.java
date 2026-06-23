package com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.entity;


import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "container_movement_outbox")
@Entity
public class ContainerMovementOutboxEntity {
	@Id
	private UUID id;
	private UUID sagaId;
	private ZonedDateTime createdAt;
	private ZonedDateTime processedAt;
	private String type;
	private String payload;
	@Enumerated(EnumType.STRING)
	private SagaStatus sagaStatus;
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;
	@Enumerated(EnumType.STRING)
	private OutboxStatus outboxStatus;
	@Version
	private int version;

}
