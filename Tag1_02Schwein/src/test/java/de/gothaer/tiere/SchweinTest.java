package de.gothaer.tiere;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SchweinTest {
    private Schwein objectUnderTest;

    private final String validName = "Piggy";
    private final String inValidName = "Elsa";


    private final int initialWeight = 10;

    @BeforeEach
    void setup() {
        objectUnderTest = new Schwein();
    }

    @Nested
    class ctors {
        @Test
        void ctor_default_objectCorrectIntializied() {
            assertEquals("Nobody", objectUnderTest.getName());
            assertThat(objectUnderTest.getName()).isEqualTo("Nobody");

            assertEquals(initialWeight, objectUnderTest.getGewicht());

        }

        @Test
        void ctor_wrongNameNull_throwsIlliegalArgumentExceptionAndErrorMessage() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Schwein(null));
            assertEquals("Unerlaubter Name", ex.getMessage());
        }

        @Test
        void ctor_wrongNameElsa_throwsIllegalArgumentException() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Schwein(inValidName));
            assertEquals("Unerlaubter Name", ex.getMessage());
        }

        @Test
        void ctor_validName_objectCorrectIntializied() {

            Schwein objectUnderTest = new Schwein(validName);
            assertEquals("Piggy", objectUnderTest.getName());
            assertEquals(initialWeight, objectUnderTest.getGewicht());
        }
    }
    @Test
    void setName_wrongNameNull_throsIlliegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()->objectUnderTest.setName(null));

    }

    @Test
    void setName_wrongNameElsa_throsIlliegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()->objectUnderTest.setName("Elsa"));

    }

    @Test
    void setName_validName_NameIsStored() {

        objectUnderTest.setName(validName);
        assertEquals(validName, objectUnderTest.getName());

    }

    @Test
    void futtern_happyDay_weightIncreased() {
        final int expectedWeight = initialWeight + 1;
        objectUnderTest.futtern();
        assertEquals(expectedWeight, objectUnderTest.getGewicht());
        assertEquals(1.0,1.0, 0.00001);
    }


}