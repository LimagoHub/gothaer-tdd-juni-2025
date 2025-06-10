package de.gothaer.collctions;

import de.gothaer.collections.Stapel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StapelTest {

    private Stapel objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }

    @Test
    void t0() {
        assertNotNull(objectUnderTest);
    }

    @Test
    void t1() {
        assertTrue(objectUnderTest.isEmpty());
    }
    @Test
    void t2() {
        objectUnderTest.push(new Object());
        assertFalse(objectUnderTest.isEmpty());
    }
}
