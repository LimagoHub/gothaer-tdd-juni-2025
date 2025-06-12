package de.gothaer.service;

import de.gothaer.dependency.Dependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ServiceUsingDependencyTest {

    @InjectMocks
    private ServiceUsingDependency objectUnderTest;

    @Mock
    private Dependency dependencyMock;

    /*@BeforeEach
    void setUp() {
        dependencyMock = mock(Dependency.class);
        objectUnderTest = new ServiceUsingDependency(dependencyMock);
    }*/

    @Test
    void barTest() {
        // Arrange
        // Recordmode
        when(dependencyMock.supplier()).thenReturn(25);
        // Replay

        var result = objectUnderTest.bar();
        result = objectUnderTest.bar();

        // Assert
        assertEquals(250, result);
        verify(dependencyMock, atLeast(1)).supplier();
    }
    @Test
    void foobarTest() {
        // Arrange
        // Recordmode
        when(dependencyMock.function(anyString())).thenReturn(3);

        // Replay

        var result = objectUnderTest.foobar();


        // Assert
        assertEquals(6, result);
        verify(dependencyMock, times(2)).function("Hallo");
    }

    @Test
    void fooTest() {
        doNothing().when(dependencyMock).consumer(anyInt());
        objectUnderTest.foo();
        verify(dependencyMock, times(1)).consumer(5);

    }

    @Test
    void xy() {
        lenient().doNothing().when(dependencyMock).consumer(anyInt());


    }
}