package fuzzy.mf.Linguistic_Variable;

import fuzzy.mf.FuzzySet;

import java.util.*;

public class LinguisticVariable {
    private final String name;
    private final double minDomain;
    private final double maxDomain;
    private final List<FuzzySet> sets;

    public LinguisticVariable(String name, double minDomain, double maxDomain) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Variable name cannot be null or empty");

        if (minDomain >= maxDomain)
            throw new IllegalArgumentException("Invalid domain range: min must be < max");

        this.name = name.trim();
        this.minDomain = minDomain;
        this.maxDomain = maxDomain;
        this.sets = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void addSet(FuzzySet set) {
        Objects.requireNonNull(set, "FuzzySet cannot be null");

        // Prevent duplicate names
        for (FuzzySet existing : sets) {
            if (existing.getName().equalsIgnoreCase(set.getName()))
                throw new IllegalArgumentException("Duplicate fuzzy set name: " + set.getName());
        }

        sets.add(set);
    }


    public List<FuzzySet> getSets() {
        return Collections.unmodifiableList(sets);
    }

    public boolean isInsideDomain(double value) {
        return value >= minDomain && value <= maxDomain;
    }

   //fuzzification
    public Map<String, Double> fuzzify(double value) {
        // Validation
        if (!isInsideDomain(value)) {
            throw new IllegalArgumentException(
                    "Value " + value + " is outside domain [" + minDomain + ", " + maxDomain + "]"
            );
        }

        Map<String, Double> result = new LinkedHashMap<>();
        for (FuzzySet set : sets) {
            result.put(set.getName(), set.degree(value));
        }

        return result;
    }

    @Override
    public String toString() {
        return "LinguisticVariable{name='" + name + "', sets=" + sets + "}";
    }

    public double getMinDomain() {
    return minDomain;
    }

    public double getMaxDomain() {
        return maxDomain;
    }


}
