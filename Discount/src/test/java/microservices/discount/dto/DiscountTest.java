package microservices.discount.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountTest {

    private Discount discountUnderTest;

    @BeforeEach
    void setUp() {
        discountUnderTest = new Discount("phone", Level.BRONZE, 0);
    }

    @Test
    void testToString() {
        assertThat(discountUnderTest.toString()).isEqualTo("Discount{Phone='phone', level=BRONZE, groomSetsNumber=0}");
    }
}
