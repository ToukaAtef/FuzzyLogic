package fuzzy.mf.rules;

import java.util.HashMap;
import java.util.Map;

public class StartRuleBased {
    Map<String, Map<String, Double>> fuzzifiedInputs;
    public RuleBase ruleBase = new RuleBase();

    public StartRuleBased(Map<String, Map<String, Double>> fuzzifiedInputs) {
        this.fuzzifiedInputs = fuzzifiedInputs;
    }
    public void makeRules() {

        ruleBase.addRule()
                .ifVar("Experience", "High")
                .and("SkillsScore", "Excellent")
                .and("Adaptability", "High")
                .then("SuitabilityScore", "Highly Suitable");

        ruleBase.addRule()
                .ifVar("Experience", "Medium")
                .and("SkillsScore", "Good")
                .and("Adaptability", "Acceptable")
                .then("SuitabilityScore", "Moderate");

        ruleBase.addRule()
                .ifVar("Experience", "Low")
                .and("SkillsScore", "Poor")
                .then("SuitabilityScore", "Unsuitable");

        ruleBase.addRule()
                .ifVar("SkillsScore", "Excellent")
                .and("Experience", "Low")
                .then("SuitabilityScore", "Acceptable");

        ruleBase.addRule()
                .ifVar("Experience", "High")
                .and("Adaptability", "Low")
                .then("SuitabilityScore", "Moderate");

        ruleBase.addRule()
                .ifVar("SkillsScore", "Good")
                .and("Adaptability", "High")
                .then("SuitabilityScore", "Moderate");

        ruleBase.addRule()
                .ifVar("Experience", "Low")
                .or("Adaptability", "Low")
                .then("SuitabilityScore", "Unsuitable");

        ruleBase.addRule()
                .ifVar("Experience", "High")
                .or("Adaptability", "High")
                .then("SuitabilityScore", "Highly Suitable");

    }

    public Map<String, Double> getResultFromRules(){
        Map<String, Double> fuzzyRuleOutput = new HashMap<>();
        for(FuzzyRule rule : ruleBase.getRules()){
            double res=rule.applyRule(fuzzifiedInputs); fuzzyRuleOutput.put(rule.consequentSet,res);
        } return fuzzyRuleOutput;
    }

//    public Map<String, Double> getResultFromRules(){
//        Map<String, Double> aggregatedOutput = new HashMap<>();
//
//        for(FuzzyRule rule : ruleBase.getRules()){
//            double firingStrength = rule.applyRule(fuzzifiedInputs);
//
//            double impliedDegree = Math.min(firingStrength, 1.0);
//
//            String setName = rule.getConsequentSet();
//
//
//            double current = aggregatedOutput.getOrDefault(setName, 0.0);
//            aggregatedOutput.put(setName, Math.max(current, impliedDegree));
//        }
//
//        return aggregatedOutput;
//    }
//

}
