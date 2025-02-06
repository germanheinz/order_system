package com.order.system.application.service.ports.input.message.listener.payment;

import com.order.system.application.service.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
