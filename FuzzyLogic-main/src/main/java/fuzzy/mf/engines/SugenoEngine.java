package fuzzy.mf.engines;

import java.util.HashMap;
import java.util.Map;

import fuzzy.mf.Linguistic_Variable.LinguisticVariable;
import fuzzy.mf.rules.FuzzyRule;
import fuzzy.mf.rules.RuleBase;

public class SugenoEngine implements InferenceEngine{
    private final Map<FuzzyRule, SugenoConsequent> consequentFunctions;
    private Map<String, Double> crispInputs;
    public SugenoEngine() {
        this.consequentFunctions = new HashMap<>();
        this.crispInputs = new HashMap<>();
    }
    public void setConsequent(FuzzyRule rule, SugenoConsequent consequent) {
        consequentFunctions.put(rule, consequent);
    }

    public void setCrispInputs(Map<String, Double> crispInputs) {
        this.crispInputs = new HashMap<>(crispInputs);
    }

    public Map<Double, Double> inference(RuleBase ruleBase, Map<String, Map<String, Double>> fuzzifiedInputs, LinguisticVariable outputVariable){
        double weightedSum = 0.0;  
        double totalWeight = 0.0;  

        for (FuzzyRule rule : ruleBase.getRules()) {
            double firingStrength = rule.applyRule(fuzzifiedInputs);
            
            if (firingStrength > 0.0) {
                SugenoConsequent consequent = consequentFunctions.get(rule);
                
                if (consequent == null) {
                    throw new IllegalStateException(
                        "No consequent function set for rule. Call setConsequent() first."
                    );
                }
                double consequentValue = consequent.compute(crispInputs);
                
                weightedSum += firingStrength * consequentValue;
                totalWeight += firingStrength;
            }
        }
        double crispOutput = (totalWeight > 0) ? weightedSum / totalWeight : 0.0;
        
        Map<Double, Double> output = new HashMap<>();
        output.put(crispOutput, 1.0);
        
        return output;

    }

    public interface SugenoConsequent {
        double compute(Map<String, Double> crispInputs);
    }

    public static class ConstantConsequent implements SugenoConsequent {
        private final double constantValue;
        
        public ConstantConsequent(double value) {
            this.constantValue = value;
        }
        
        @Override
        public double compute(Map<String, Double> crispInputs) {
            return constantValue;
        }
        
        @Override
        public String toString() {
            return "Constant(" + constantValue + ")";
        }
    }

    
    // First-order Sugeno: linear function
    // output = c0 + c1*x1 + c2*x2 + ...
    //Example: THEN output = 10 + 5*Experience + 3*Skills

    public static class LinearConsequent implements SugenoConsequent {
        private final double constant;  // c0
        private final Map<String, Double> coefficients;  // c1, c2, ...
       
        public LinearConsequent(double constant, Map<String, Double> coefficients) {
            this.constant = constant;
            this.coefficients = new HashMap<>(coefficients);
        }
        
        public LinearConsequent(Map<String, Double> coefficients) {
            this(0.0, coefficients);
        }
        
        @Override
        public double compute(Map<String, Double> crispInputs) {
            double result = constant;
            
            for (Map.Entry<String, Double> entry : coefficients.entrySet()) {
                String variableName = entry.getKey();
                double coefficient = entry.getValue();
                
                Double inputValue = crispInputs.get(variableName);
                if (inputValue != null) {
                    result += coefficient * inputValue;
                }
            }
            
            return result;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(constant);
            for (Map.Entry<String, Double> entry : coefficients.entrySet()) {
                sb.append(" + ").append(entry.getValue())
                  .append("*").append(entry.getKey());
            }
            return sb.toString();
        }
    }

}
