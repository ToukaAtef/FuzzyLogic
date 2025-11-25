package fuzzy.mf;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuzzySetTest {
    @Test
    void degreeDelegatesToMF() {
        FuzzySet low = new FuzzySet("Low", new TriangularMF(0, 0, 10));
        assertEquals(1.0, low.degree(0.0), 1e-9);
        assertEquals(0.0, low.degree(11.0), 1e-9);
    }

    @Test
    void invalidNameOrNullMF() {
        assertThrows(IllegalArgumentException.class, () -> new FuzzySet("", new TriangularMF(0,1,2)));
        assertThrows(NullPointerException.class, () -> new FuzzySet("x", null));
    }
}
