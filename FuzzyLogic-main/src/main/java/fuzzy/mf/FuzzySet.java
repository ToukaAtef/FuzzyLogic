package fuzzy.mf;
import java.util.Objects;

public final class FuzzySet {
    private final String name;
    private final MembershipFunction mf;

    public FuzzySet(String name, MembershipFunction mf) {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null");
        String trimmed = name.trim();
        if (trimmed.isEmpty())
            throw new IllegalArgumentException("name cannot be empty");
        this.name = trimmed;
        this.mf = Objects.requireNonNull(mf, "MembershipFunction cannot be null");
    }

    public String getName() {
        return name;
    }

    public MembershipFunction getMembershipFunction() {
        return mf;
    }

    public double degree(double x) {
        return mf.compute(x);
    }

    @Override
    public String toString() {
        return "FuzzySet{name='" + name + "', mf=" + mf + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FuzzySet)) return false;
        FuzzySet fuzzySet = (FuzzySet) o;
        return name.equals(fuzzySet.name) && mf.equals(fuzzySet.mf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mf);
    }

    public static FuzzySet of(String name, MembershipFunction mf) {
        return new FuzzySet(name, mf);
    }
}
