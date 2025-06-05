package com.vikas.financetracker.listener;

@Component
public class ExpenseEventListener {

    @KafkaListener(topics = "expense.created.topic", groupId = "expense-tracker-consumer")
    public void listen(ExpenseCreatedEvent event) {
        System.out.println("📥 Received event from Kafka: " + event);
        expenseService.createFromEvent(event); // Example service method to persist

    }
}

