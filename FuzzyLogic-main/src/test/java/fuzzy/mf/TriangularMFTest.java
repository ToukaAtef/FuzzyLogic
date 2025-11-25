package fuzzy.mf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangularMFTest {
    private static final double epsilon=1e-9;

    @Test
    void testBasicTriangle() {
        TriangularMF tr = new TriangularMF(0.0, 5.0, 10.0);

        assertEquals(0.0, tr.compute(-1.0), epsilon);
        assertEquals(0.0, tr.compute(0.0), epsilon);
        assertEquals(0.0, tr.compute(10.0), epsilon);
        assertEquals(1.0, tr.compute(5.0), epsilon);

        assertEquals(0.5, tr.compute(2.5), 1e-9);
        assertEquals(0.5, tr.compute(7.5), 1e-9);
    }

    @Test
    void testDegenerateSides() {
        TriangularMF leftVertical = new TriangularMF(0.0, 0.0, 10.0);
        assertEquals(1.0, leftVertical.compute(0.0), epsilon);

        TriangularMF rightVertical = new TriangularMF(0.0, 10.0, 10.0);
        assertEquals(1.0, rightVertical.compute(10.0), epsilon);
    }

    @Test
    void testInvalidOrdering() {
        assertThrows(IllegalArgumentException.class, () -> new TriangularMF(2, 1, 3));
    }
}
