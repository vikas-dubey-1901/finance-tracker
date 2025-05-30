package com.vikas.test.financeTracker;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testAddTransaction() {
        Transaction tx = Transaction.builder()
                .userId("user1")
                .amount(BigDecimal.valueOf(150))
                .category("Food")
                .type(Transaction.Type.EXPENSE)
                .build();

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenReturn(Mono.just(tx));

        StepVerifier.create(transactionService.addTransaction(tx))
                .expectNext(tx)
                .verifyComplete();
    }
}

