package fuzzy.mf;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrapezoidalMFTest {
    private static final double epsilon = 1e-9;

    @Test
    void testBasicTrapezoid() {
        TrapezoidalMF t = new TrapezoidalMF(0.0, 2.0, 8.0, 10.0);

        assertEquals(0.0, t.compute(-1.0), epsilon);
        assertEquals(0.0, t.compute(0.0), epsilon);
        assertEquals(1.0, t.compute(2.0), epsilon);
        assertEquals(1.0, t.compute(5.0), epsilon);
        assertEquals(1.0, t.compute(8.0), epsilon);
        assertEquals(0.0, t.compute(10.0), epsilon);

        assertEquals(0.5, t.compute(1.0), 1e-9);
        assertEquals(0.5, t.compute(9.0), 1e-9);
    }

    @Test
    void testDegeneratePlateau() {
        TrapezoidalMF t = new TrapezoidalMF(0, 2, 2, 5);
        assertEquals(1.0, t.compute(2.0), epsilon);
    }

    @Test
    void testInvalidOrdering() {
        assertThrows(IllegalArgumentException.class, () -> new TrapezoidalMF(0, 10, 5, 15));
    }
}
