package com.order.system.application.service.ports.input.message.listener.payment;

import com.order.system.application.service.dto.message.PaymentResponse;

public interface NotifiacionResponseMessageListener {

    void notifiactionSent(PaymentResponse paymentResponse);

    void notificationFailed(PaymentResponse paymentResponse);
}
