import controller.Controller;
import dao.OrderDAO;
import dao.SimulationDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.ISimulatorUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ControllerTest {

    @Mock
    private ISimulatorUI ui;

    @Mock
    private SimulationDAO sdao;

    @Mock
    private OrderDAO odao;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // testataan controllerin luonti
    @Test
    public void testControllerInitialization() {
        assertNotNull(controller);
    }

    // testataan tiedon hakua ui:sta
    @Test
    public void testGetInterval() {
        when(ui.getOrderInterval()).thenReturn(5);
        double interval = controller.getOrderInterval();
        assertEquals(5, interval);
    }

    // testataan tiedonsaanti databasesta
    @Test
    public void testDatabaseInteraction() {
        assertNotNull(sdao.findAll());
    }


}