package fuzzy.mf;
import java.util.Objects;

//      mu(x) = exp(-0.5 * ((x - mean) / sigma)^2)
public final class GaussianMF implements MembershipFunction {
    private final double mean;
    private final double sigma;

    public GaussianMF(double mean, double sigma) {
        if (Double.isNaN(mean) || Double.isNaN(sigma)) {
            throw new IllegalArgumentException("Parameters must be numbers");
        }
        if (!(sigma > 0.0)) {
            throw new IllegalArgumentException("sigma must be > 0 for GaussianMF");
        }
        this.mean = mean;
        this.sigma = sigma;
    }

    @Override
    public double compute(double x) {
        if (Double.isNaN(x)) return 0.0;
        final double z = (x - mean) / sigma;
        final double v = Math.exp(-0.5 * z * z);
        if (v <= 0.0)
            return 0.0;
        if (v >= 1.0)
            return 1.0;
        return v;
    }

    public double getMean() { return mean; }
    public double getSigma() { return sigma; }

    @Override
    public String toString() {
        return "GaussianMF(mean=" + mean + ", sigma=" + sigma + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GaussianMF)) return false;
        GaussianMF g = (GaussianMF) o;
        return Double.compare(g.mean, mean) == 0 && Double.compare(g.sigma, sigma) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mean, sigma);
    }
}
