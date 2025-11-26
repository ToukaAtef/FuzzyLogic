package fuzzy.mf.defuzzifiers;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MeanOfMaximumDefuzzifier implements Defuzzifier {
    
    @Override
    public double defuzzify(Map<Double, Double> aggregatedOutput) {
        if (aggregatedOutput == null || aggregatedOutput.isEmpty()) {
            throw new IllegalArgumentException("Aggregated output cannot be null or empty");
        }

        double maxMembership = Double.NEGATIVE_INFINITY;
        
        for (Double membership : aggregatedOutput.values()) {
            if (membership > maxMembership) {
                maxMembership = membership;
            }
        }

        if (maxMembership <= 0.0) {
            return 0.0;
        }

        List<Double> maxPoints = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : aggregatedOutput.entrySet()) {
            if (Math.abs(entry.getValue() - maxMembership) < 1e-9) {
                maxPoints.add(entry.getKey());
            }
        }

        if (maxPoints.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Double point : maxPoints) {
            sum += point;
        }

        return sum / maxPoints.size();
    }

    @Override
    public String toString() {
        return "MeanOfMaximumDefuzzifier";
    }
}
