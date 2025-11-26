package fuzzy.mf.defuzzifiers;

import java.util.Map;

public class SugenoWeightedAverageDefuzzifier implements Defuzzifier {
    
    @Override
    public double defuzzify(Map<Double, Double> sugenoOutput) {
        if (sugenoOutput == null || sugenoOutput.isEmpty()) {
            throw new IllegalArgumentException("Sugeno output cannot be null or empty");
        }

      
        if (sugenoOutput.size() == 1) {
            return sugenoOutput.keySet().iterator().next();
        }

        // If multiple values, compute weighted average
        double weightedSum = 0.0;
        double totalWeight = 0.0;

        for (Map.Entry<Double, Double> entry : sugenoOutput.entrySet()) {
            double value = entry.getKey();
            double weight = entry.getValue();
            
            weightedSum += value * weight;
            totalWeight += weight;
        }

        if (totalWeight == 0.0) {
            return 0.0;
        }

        return weightedSum / totalWeight;
    }

    @Override
    public String toString() {
        return "SugenoWeightedAverageDefuzzifier";
    }}