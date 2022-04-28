package microservices.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PetTest {

    private Pet petUnderTest;

    @BeforeEach
    void setUp() {
        petUnderTest = new Pet(Species.CAT, "name", "breed", 0, 0, "ownersPhone");
    }

    @Test
    void testToString() {
        assertThat(petUnderTest.toString()).isEqualTo("Pet{id=0, species=CAT, name='name', breed='breed', age=0, weight='0', ownersPhone='ownersPhone'}");
    }
}
