package de.gothaer.gui.presenter;

import de.gothaer.gui.Euro2DollarRechnerView;
import de.gothaer.model.Euro2DollarRechner;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.verification.VerificationMode;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Euro2DollarPresenterImplTest {

    @InjectMocks
    private Euro2DollarPresenterImpl objectUnderTest;

    @Mock
    private Euro2DollarRechnerView viewMock;
    @Mock
    private Euro2DollarRechner modelMock;

    @Test
    void onBeenden_happyDay_closeWirdAufgerufen() {
        //Arrange
        doNothing().when(viewMock).close();

        //Act
        objectUnderTest.onBeenden();

        //Assert
        verify(viewMock).close();
    }

    @Test
    void onPopulateItems_happyDay_maskInitilized() {
        objectUnderTest.onPopulateItems();

        InOrder inOrder = Mockito.inOrder(viewMock);
        inOrder.verify(viewMock).setEuro("0");
        inOrder.verify(viewMock).setDollar("0");
        inOrder.verify(viewMock).setRechnenEnabled(true);
    }

    @Test
    void onRechnen_keineZahlInEuroFeld_SchreibeFehlerInDollarFeld() {
        when(viewMock.getEuro()).thenReturn("NotANumber");

        objectUnderTest.onRechnen();

        verify(viewMock).setDollar("Kann nicht konvertiert werden.");

    }

    @Test
    void onRechnen_nullValueInEuroFeld_SchreibefehlerInDollarFeld() {
        when(viewMock.getEuro()).thenReturn(null);

        objectUnderTest.onRechnen();

        verify(viewMock).setDollar("Kann nicht konvertiert werden.");
    }

    @Test
    void onRechnen_UnexspectedRuntimeExceptionInUnderlyineService_SchreibefehlerInDollarFeld() {
        when(modelMock.calculateEuro2Dollar(anyDouble())).thenThrow(new ArithmeticException());
        when(viewMock.getEuro()).thenReturn("7");
        objectUnderTest.onRechnen();

        verify(viewMock).setDollar("Wechselkurs nicht verfuegbar.");
    }

    /* (non-Javadoc)
     * @see de.gui.presenter.IEuro2DollarPresenter#rechnen()
     *
     * Eurowert als String aus der Maske holen
     * Eurowert in double konvertieren (bei Problemen jeder Art -> Fehlermeldung in Dollarfeld)
     * Dollarwert berechnen -> in wir das Model aufrufen (Fehlerbehandlung wie oben)
     * Dollarwert als String in das Dollarfeld schreiben
     *
     *
     */

    @Test
    void onRechnen_validValueInEuroField_PassToService() {
        when(viewMock.getEuro()).thenReturn("7");
        when(modelMock.calculateEuro2Dollar(anyDouble())).thenReturn(2.3);
        objectUnderTest.onRechnen();
        verify(modelMock).calculateEuro2Dollar(7);
    }

    @Test
    void onRechnen_happyDay_resultInDollarFeld() {
        when(viewMock.getEuro()).thenReturn("7.0");
        when(modelMock.calculateEuro2Dollar(anyDouble())).thenReturn(2.3);
        objectUnderTest.onRechnen();
        InOrder inOrder = Mockito.inOrder(modelMock, viewMock);
        inOrder.verify(modelMock).calculateEuro2Dollar(7.0);
        inOrder.verify(viewMock).setDollar("2,30");
    }

    @ParameterizedTest
    @MethodSource("updateRechnenActionStateTestData")
    void updateRechnenActionState_actionActivationTest(String input, boolean expected) {
        when(viewMock.getEuro()).thenReturn(input);
        objectUnderTest.updateRechnenActionState();
        verify(viewMock).setRechnenEnabled(expected);
    }

    private static Stream<Arguments> updateRechnenActionStateTestData() {
        return Stream.of(Arguments.of(null, false), Arguments.of("", false), Arguments.of("Herbert", false), Arguments.of("3.14", true));
    }
}