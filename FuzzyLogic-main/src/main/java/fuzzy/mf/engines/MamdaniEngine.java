package fuzzy.mf.engines;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fuzzy.mf.Linguistic_Variable.LinguisticVariable;
import fuzzy.mf.rules.FuzzyRule;
import fuzzy.mf.rules.RuleBase;

public class MamdaniEngine implements InferenceEngine {
    private final String implicationOp; // min or product
    private final String aggregationOp; // max
    private final double output;

    public MamdaniEngine(String implicationOp, String aggregationOp, Double output){
        this.implicationOp = implicationOp;
        this.aggregationOp = aggregationOp;
        this.output = output;
    }

    
    //Default constructor: min implication, max aggregation, 1.0 resolution
    public MamdaniEngine() {
        this("min", "max", 1.0);
    }

    public Map<Double, Double> inference(RuleBase ruleBase, Map<String, Map<String, Double>> fuzzifiedInputs, LinguisticVariable outputVariable){
        Map<Double, Double> aggregatedOutput = new TreeMap<>();
        List<FuzzyRule>rules = ruleBase.getRules();
        for(FuzzyRule rule: rules){
            double firingStrength = rule.applyRule(fuzzifiedInputs);
            
            if (firingStrength <= 0.0)
                continue;
            
            String consequentSetName = rule.getConsequentSet();
            Map<Double, Double> implicatedSet = applyImplication(outputVariable, consequentSetName, firingStrength);
            aggregatedOutput = aggregateOutputs(aggregatedOutput, implicatedSet);
        }
        return aggregatedOutput;
    }

    private Map<Double, Double> applyImplication(LinguisticVariable outputVariable, String fuzzySetName, Double firingStrength){
        Map<Double, Double> result = new TreeMap<>();
        double min = outputVariable.getMinDomain();
        double max = outputVariable.getMaxDomain();

        for (double x = min; x <= max; x += output) {
            double membership = 0.0;

            for(var set: outputVariable.getSets()){
                if(set.getName().equalsIgnoreCase(fuzzySetName)){
                    membership = set.degree(x);
                    break;
                }
            }
            double implicated;
            if(implicationOp.equalsIgnoreCase("min")){
                implicated = Math.min(firingStrength, membership);
            }
            else if(implicationOp.equalsIgnoreCase("product")){
                implicated = firingStrength * membership;
            }
            else{
                throw new IllegalArgumentException("Unknown implication operator: " + implicationOp);
            }
            result.put(x, implicated);
        }

        return result;
    }

    private Map<Double, Double> aggregateOutputs(Map<Double, Double> out1, Map<Double, Double> out2){
        Map<Double, Double> result = new TreeMap<>(out1);
        for (Map.Entry<Double, Double> entry : out2.entrySet()) {
            double x = entry.getKey();
            double value = entry.getValue();

            if (aggregationOp.equalsIgnoreCase("max")) {
                result.merge(x, value, Math::max);
            } else {
                throw new IllegalArgumentException("Unknown aggregation operator: " + aggregationOp);
            }
        }

        return result;
    }

    public String getImplicationOperator() { return implicationOp; }
    public String getAggregationOperator() { return aggregationOp; }
    public double getOutputResolution() { return output; }


}

