package com.hooshmand.shipping.booking.service.domain.valueobject;

import com.hooshmand.shipping.system.domain.valuobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID>  {
	public TrackingId(UUID value) {
		super(value);
	}
}
