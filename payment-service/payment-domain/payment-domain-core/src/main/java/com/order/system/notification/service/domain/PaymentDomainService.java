package com.order.system.notification.service.domain;

import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.notification.service.domain.entity.CreditEntry;
import com.order.system.notification.service.domain.entity.CreditHistory;
import com.order.system.notification.service.domain.entity.Payment;
import com.order.system.notification.service.domain.event.PaymentCancelledEvent;
import com.order.system.notification.service.domain.event.PaymentCompletedEvent;
import com.order.system.notification.service.domain.event.PaymentEvent;
import com.order.system.notification.service.domain.event.PaymentFailedEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages,
                                            DomainEventPublisher<PaymentCompletedEvent>
                                                    paymentCompletedEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages, DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);
}