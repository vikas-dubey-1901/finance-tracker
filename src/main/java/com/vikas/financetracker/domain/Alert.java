package com.vikas.financetracker.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;

    private String userId;

    private String category;

    private int year;

    private int month;

    private String message;

    private Instant createdAt;
}

