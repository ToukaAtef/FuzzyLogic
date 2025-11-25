package fuzzy.mf;
import java.util.Objects;

public final class TriangularMF implements MembershipFunction {
    private final double a;
    private final double b;
    private final double c;

    public TriangularMF(double a, double b, double c) {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c)) {
            throw new IllegalArgumentException("Parameters must be numbers");
        }
        if (a > b || b > c) {
            throw new IllegalArgumentException("Require a<=b<=c for triangular MF");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double compute(double x) {
        if (Double.isNaN(x)) return 0.0;

        if (a == b && b == c) {
            return (x == a) ? 1.0 : 0.0;
        }

        //Left
        if (a == b) {
            if (x == b) return 1.0;
            if (x < b || x > c) return 0.0;
            return (c - x) / (c - b);
        }

        //Right
        if (b == c) {
            if (x == b) return 1.0;
            if (x < a || x > b) return 0.0;
            return (x - a) / (b - a);
        }

        //Normal
        if (x <= a || x >= c) return 0.0;
        if (x == b) return 1.0;
        if (x < b)
            return (x - a) / (b - a);

        return (c - x) / (c - b);
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }

    @Override
    public String toString() {
        return "TriangularMF(" + a + "," + b + "," + c + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TriangularMF)) return false;
        TriangularMF that = (TriangularMF) o;
        return Double.compare(that.a, a) == 0 &&
                Double.compare(that.b, b) == 0 &&
                Double.compare(that.c, c) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
