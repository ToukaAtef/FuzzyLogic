package fuzzy.mf.defuzzifiers;

import java.util.Map;
public class CentroidDefuzzifier implements Defuzzifier {
    
    @Override
    public double defuzzify(Map<Double, Double> aggregatedOutput) {
        if (aggregatedOutput == null || aggregatedOutput.isEmpty()) {
            throw new IllegalArgumentException("Aggregated output cannot be null or empty");
        }

        double numerator = 0.0;
        double denominator = 0.0;

        for (Map.Entry<Double, Double> entry : aggregatedOutput.entrySet()) {
            double x = entry.getKey();
            double membership = entry.getValue();
            
            numerator += x * membership;
            denominator += membership;
        }

        if (denominator == 0.0) {
            return 0.0;
        }

        return numerator / denominator;
    }

    @Override
    public String toString() {
        return "CentroidDefuzzifier";
    }
}