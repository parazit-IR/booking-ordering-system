package com.hooshmand.shipping.system.outbox;

public interface OutboxScheduler {
	void processOutboxMessage();
}
