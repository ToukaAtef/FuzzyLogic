package fuzzy.mf;
import java.util.Objects;

public final class TrapezoidalMF implements MembershipFunction {
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public TrapezoidalMF(double a, double b, double c, double d) {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Parameters must be numbers");
        }
        if (a>b || b>c || c>d) {
            throw new IllegalArgumentException("Require a<=b<=c<=d for trapezoidal MF");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double compute(double x) {
        if (Double.isNaN(x)) return 0.0;

        if (x <= a || x >= d) {
            if (a == b && b == c && c == d && x == a) return 1.0;
            return 0.0;
        }

        if (x < b) {
            if (b == a) return 1.0;
            return zeroOrOne((x - a) / (b - a));
        }

        if (x <= c) {
            return 1.0;
        }

        if (x > c) {
            if (d == c) return 1.0;
            return zeroOrOne((d - x) / (d - c));
        }

        return 0.0;
    }

    private double zeroOrOne(double v) {
        if (v <= 0.0) return 0.0;
        if (v >= 1.0) return 1.0;
        return v;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    public double getD() { return d; }

    @Override
    public String toString() {
        return "TrapezoidalMF(" + a + "," + b + "," + c + "," + d + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrapezoidalMF)) return false;
        TrapezoidalMF that = (TrapezoidalMF) o;
        return Double.compare(that.a, a) == 0 &&
                Double.compare(that.b, b) == 0 &&
                Double.compare(that.c, c) == 0 &&
                Double.compare(that.d, d) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d);
    }
}
