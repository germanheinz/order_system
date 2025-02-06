package com.order.system.application.service.ports.input.service;

import com.order.system.application.service.dto.create.CreateOrderCommand;
import com.order.system.application.service.dto.create.CreateOrderResponse;
import com.order.system.application.service.dto.track.TrackOrderQuery;
import com.order.system.application.service.dto.track.TrackOrderResponse;

import jakarta.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
