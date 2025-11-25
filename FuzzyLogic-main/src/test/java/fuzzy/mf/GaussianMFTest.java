package fuzzy.mf;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GaussianMFTest {
    @Test
    void testGaussianPeakAndSymmetry() {
        GaussianMF g = new GaussianMF(0.0, 2.0);

        assertEquals(1.0, g.compute(0.0), 1e-9);
        double left = g.compute(-2.0);
        double right = g.compute(2.0);
        assertEquals(left, right, 1e-12);

        assertTrue(g.compute(10.0) < 1e-4);
    }

    @Test
    void testInvalidSigma() {
        assertThrows(IllegalArgumentException.class, () -> new GaussianMF(0.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new GaussianMF(0.0, -1.0));
    }
}
