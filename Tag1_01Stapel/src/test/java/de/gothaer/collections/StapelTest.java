package de.gothaer.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StapelTest {

    private Stapel objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }

    @Test
    @DisplayName("Given empty Stack when isEmpty called it should return true")
    void isEmpty_EmptyStack_returnsTrue() {
        // Arrange


        // Act
        var result = objectUnderTest.isEmpty();

        // Assertion
        assertTrue(result);

    }
    @Test
    void isEmpty_NotEmptyStack_returnsFalse() throws Exception{
        // Arrange


        objectUnderTest.push(new Object());
        // Act
        var result = objectUnderTest.isEmpty();

        // Assertion
        assertFalse(result);

    }

    @Test
    void push_fillupUpLimit_noExceptionIsThrown() throws Exception{
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(()->objectUnderTest.push(new Object()));
        }

    }

    @Test
    void push_overflow_throwStapelExeption() throws Exception{
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(()->objectUnderTest.push(new Object()));
        }
        StapelException ex = assertThrows(StapelException.class, ()->objectUnderTest.push(new Object()));
        assertEquals("Overflow", ex.getMessage());
    }

}