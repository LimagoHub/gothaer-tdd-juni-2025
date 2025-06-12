package org.example;

import org.example.io.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ComputerPlayerTest {

    @InjectMocks
    private ComputerPlayer objectUnderTest;

    @Mock
    private Writer writerMock;


    @ParameterizedTest
    @CsvSource({"20,3", "21,1", "22,1", "23,2","40,3", "41,1", "42,1", "43,2"})
    void doTurn(int remainingStones, int expectedTurn) {
        assertEquals(expectedTurn,objectUnderTest.doTurn(remainingStones));
        verify(writerMock,Mockito.times(1)).write("Computer nimmt "+ expectedTurn + " Steine.");
    }

}