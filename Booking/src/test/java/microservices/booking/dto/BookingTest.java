package microservices.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookingTest {

    @Mock
    private ArrayList<Pet> mockPet;

    private Booking bookingUnderTest;

    @BeforeEach
    void setUp() {
        bookingUnderTest = new Booking(LocalDateTime.of(2020, 1, 1, 0, 0, 0), "phone", false, mockPet, "services", 0.0);
    }

    @Test
    void testToString() {
        assertThat(bookingUnderTest.toString()).isEqualTo("result");
    }

}
