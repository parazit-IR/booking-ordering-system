package com.hooshmand.shipping.system.saga;

public enum SagaStatus {
	STARTED, FAILED, SUCCEEDED, PROCESSING, COMPENSATING, COMPENSATED
}
