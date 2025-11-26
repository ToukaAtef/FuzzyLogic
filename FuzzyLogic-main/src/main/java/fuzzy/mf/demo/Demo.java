package fuzzy.mf.demo;
import fuzzy.mf.*;
import fuzzy.mf.Linguistic_Variable.LinguisticVariable;
import fuzzy.mf.rules.StartRuleBased;
import fuzzy.mf.engines.MamdaniEngine;
import fuzzy.mf.engines.SugenoEngine;
import fuzzy.mf.rules.RuleBase;
import fuzzy.mf.rules.FuzzyRule;
import fuzzy.mf.defuzzifiers.*;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {

        LinguisticVariable Experience = new LinguisticVariable("Experience", 0, 10);
        Experience.addSet(new FuzzySet("Low", new TriangularMF(0, 0, 5)));
        Experience.addSet(new FuzzySet("Medium", new TriangularMF(0,5,10)));
        Experience.addSet(new FuzzySet("High", new  TriangularMF(5,5,10)));

        LinguisticVariable  SkillsScore  = new LinguisticVariable("SkillsScore", 0, 10);
        SkillsScore.addSet(new FuzzySet("Poor", new TriangularMF(0, 0, 3)));
        SkillsScore.addSet(new FuzzySet("Good", new TriangularMF(0,5,10)));
        SkillsScore.addSet(new FuzzySet("Excellent", new  TriangularMF(6,6,10)));

        LinguisticVariable  Adaptability  = new LinguisticVariable("Adaptability", 0, 10);
        Adaptability.addSet(new FuzzySet("Low", new TriangularMF(0, 0, 4)));
        Adaptability.addSet(new FuzzySet("Acceptable", new TriangularMF(0,5,10)));
        Adaptability.addSet(new FuzzySet("High", new  TriangularMF(6,6,10)));


        LinguisticVariable  SuitabilityScore  = new LinguisticVariable("SuitabilityScore", 0, 100);
        SuitabilityScore.addSet(new FuzzySet("Unsuitable", new TriangularMF(0, 0, 40)));
        SuitabilityScore.addSet(new FuzzySet("Moderate", new TriangularMF(0,50,100)));
        SuitabilityScore.addSet(new FuzzySet("Highly Suitable", new  TriangularMF(70,70,100)));

        // ---------------- SAMPLE INPUT ----------------
        double ExperienceInput = 8;
        double SkillsScoreInput = 5;
        double AdaptabilityInput = 7;

        System.out.println("\n=== Fuzzification Results ===");

        
        Map<String, Map<String, Double>> fuzzifiedInputs = new HashMap<>();

        try {
            System.out.println("\nExperience:");
            Experience.fuzzify(ExperienceInput).forEach((k, v) -> System.out.println(k + " -> " + v));

            System.out.println("\nSkillsScore:");
            SkillsScore.fuzzify(SkillsScoreInput).forEach((k, v) -> System.out.println(k + " -> " + v));

            System.out.println("\nAdaptability:");
            Adaptability.fuzzify(AdaptabilityInput).forEach((k, v) -> System.out.println(k + " -> " + v));

        } catch (Exception e) {
            System.err.println("\nFuzzification Failed: " + e.getMessage());
        }

        System.out.println("\n=== RuleBased Results ===");

        RuleBase ruleBase = null;
        try {
            Map<String, Double> experienceFuzz = Experience.fuzzify(ExperienceInput);
            Map<String, Double> skillsFuzz   = SkillsScore.fuzzify(SkillsScoreInput);
            Map<String, Double> adaptabilityFuzz = Adaptability.fuzzify(AdaptabilityInput);
            fuzzifiedInputs.put("Experience", experienceFuzz);
            fuzzifiedInputs.put("SkillsScore", skillsFuzz);
            fuzzifiedInputs.put("Adaptability", adaptabilityFuzz);

           // start rule
            StartRuleBased startRuleBased=new StartRuleBased(fuzzifiedInputs);
            startRuleBased.makeRules();
            ruleBase = startRuleBased.ruleBase;
            Map<String, Double> fuzzyRuleOutput= startRuleBased.getResultFromRules();
            for(Map.Entry<String, Double> entry : fuzzyRuleOutput.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }

        }
        catch (Exception e) {
            System.err.println("\nRuleBased Failed: " + e.getMessage());
        }

        // ============================================
        // DEFUZZIFICATION SECTION
        // ============================================
        
        System.out.println("\n=== Defuzzification Results ===");

        try {
            
            Map<String, Double> crispInputs = new HashMap<>();
            crispInputs.put("Experience", ExperienceInput);
            crispInputs.put("SkillsScore", SkillsScoreInput);
            crispInputs.put("Adaptability", AdaptabilityInput);

          MamdaniEngine mamdaniEngine = new MamdaniEngine();
            Map<Double, Double> mamdaniOutput = mamdaniEngine.inference(ruleBase, fuzzifiedInputs, SuitabilityScore); 

            CentroidDefuzzifier centroid = new CentroidDefuzzifier();
            double centroidResult = centroid.defuzzify(mamdaniOutput);
            System.out.println("Crisp Output (Centroid): " + String.format("%.2f", centroidResult));

            MeanOfMaximumDefuzzifier mom = new MeanOfMaximumDefuzzifier();
            double momResult = mom.defuzzify(mamdaniOutput);
            System.out.println("Crisp Output (Mean of Maximum): " + String.format("%.2f", momResult));


            SugenoEngine sugenoEngine = new SugenoEngine();

            for (FuzzyRule rule : ruleBase.getRules()) { 
                String consequentSet = rule.getConsequentSet();
                double constantValue;

                if (consequentSet.equals("Highly Suitable")) {
                    constantValue = 85.0;
                } else if (consequentSet.equals("Moderate")) {
                    constantValue = 50.0;
                } else {
                    constantValue = 20.0;
                }

                sugenoEngine.setConsequent(rule, new SugenoEngine.ConstantConsequent(constantValue));
            }

            sugenoEngine.setCrispInputs(crispInputs);
            Map<Double, Double> sugenoOutput = sugenoEngine.inference(ruleBase, fuzzifiedInputs, SuitabilityScore);
            SugenoWeightedAverageDefuzzifier sugenoDefuzz = new SugenoWeightedAverageDefuzzifier();
            double sugenoResult = sugenoDefuzz.defuzzify(sugenoOutput);
            System.out.println("Crisp Output (Sugeno Weighted Average): " + String.format("%.2f", sugenoResult));

            System.out.println("\n=== Comparison of Defuzzification Methods ===");
            System.out.println("Centroid:              " + String.format("%.2f", centroidResult));
            System.out.println("Mean of Maximum:       " + String.format("%.2f", momResult));
            System.out.println("Sugeno Weighted Avg:   " + String.format("%.2f", sugenoResult));

        } catch (Exception e) {
            System.err.println("\nDefuzzification Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}