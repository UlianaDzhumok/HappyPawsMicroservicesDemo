package microservices.payment.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    private Payment paymentUnderTest;

    @BeforeEach
    void setUp() {
        paymentUnderTest = new Payment(LocalDateTime.of(2020, 1, 1, 0, 0, 0), "phone", 0);
    }

    @Test
    void testToString() {
        assertThat(paymentUnderTest.toString()).isEqualTo("Payment{id=0, date=2020-01-01T00:00, phone='phone', bookingId=0, price=0.0}");
    }
}
