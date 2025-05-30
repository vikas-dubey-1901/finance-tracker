package com.vikas.test.financeTracker;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testAddTransaction() {
        Transaction tx = Transaction.builder()
                .userId("testuser")
                .amount(BigDecimal.valueOf(200))
                .category("Transport")
                .type(Transaction.Type.EXPENSE)
                .description("Bus fare")
                .build();

        webTestClient.post().uri("/api/v1/transactions")
                .bodyValue(tx)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.category").isEqualTo("Transport");
    }
}

