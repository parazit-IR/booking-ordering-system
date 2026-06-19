package com.hooshmand.shipping.service.domain.ports.input.service;

import com.hooshmand.shipping.service.domain.dto.create.CreateBookingCommand;
import com.hooshmand.shipping.service.domain.dto.create.CreateBookingResponse;

import javax.validation.Valid;

public interface BookingApplicationService {

	CreateBookingResponse createBooking(@Valid CreateBookingCommand createBookingCommand);

}
