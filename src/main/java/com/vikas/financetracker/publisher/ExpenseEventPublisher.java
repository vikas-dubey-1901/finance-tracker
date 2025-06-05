package com.vikas.financetracker.publisher;

import com.vikas.financetracker.event.ExpenseCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ExpenseEventPublisher {

    private final KafkaTemplate<String, ExpenseCreatedEvent> kafkaTemplate;

    public ExpenseEventPublisher(KafkaTemplate<String, ExpenseCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> publishExpenseCreatedEvent(ExpenseCreatedEvent event) {
        return Mono.fromFuture(() -> kafkaTemplate.send("expense.created.topic", event.getExpenseId(), event).completable());
    }
}
