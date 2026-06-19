package com.hooshmand.shipping.service.domain.ports;

import com.hooshmand.shipping.service.domain.ports.output.repository.BookingRepository;
import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookingSagaHelper {
	private final BookingRepository bookingRepository;

	public BookingSagaHelper(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}


	public SagaStatus bookingStatusToSagaStatus(BookingStatus bookingStatus) {
		switch (bookingStatus) {
//			case PENDING:
//				return SagaStatus;
			case APPROVED:
				return SagaStatus.SUCCEEDED;
//			case CANCELLING:
//				return SagaStatus.COMPENSATING;
//			case CANCELLED:
//				return SagaStatus.COMPENSATED;
			default:
				return SagaStatus.STARTED;
		}
	}
}
