package fuzzy.mf.rules;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FuzzyRule {
    Map<String, String> antecedents;
    String consequentVariable;
    String consequentSet;

    AndRulesOp andOp;
    OrRulesOp orOp;

    public FuzzyRule(Map<String, String> antecedents, String consequentVariable, String consequentSet, AndRulesOp andOp, OrRulesOp orOp) {
        this.antecedents = antecedents;
        this.consequentVariable = consequentVariable;
        this.consequentSet = consequentSet;
        this.andOp = andOp;
        this.orOp = orOp;

    }
    public void setAndOp(AndRulesOp op){
        this.andOp = op;
    }
    public void setOrOp(OrRulesOp op){
        this.orOp = op;
    }

    public double applyRule(Map<String, Map<String, Double>> fuzzifiedInputs){
        boolean setOp= andOp != null;
        double res=setOp?1.0:0.0;
//        System.out.println(antecedents);
        for(Map.Entry<String,String> entry: this.antecedents.entrySet()){
            String antecedent = entry.getKey(); // Experiance
            String consequent = entry.getValue(); // High
           // System.out.println("antecedent "+antecedent);
           // System.out.println("consequent "+consequent);
            double value=fuzzifiedInputs.get(antecedent).get(consequent); // 0.8
            if(setOp){
                if(andOp == AndRulesOp.MIN)
                    res = Math.min(res, value);
                else if(andOp == AndRulesOp.PRODUCT)
                    res = res * value;
            } else {
                if(orOp == OrRulesOp.MAX)
                    res = Math.max(res, value);
                else if(orOp == OrRulesOp.SUM)
                    res = Math.min(1.0, res + value);
            }


        }
        return res;
    }


    public String getConsequentSet() {
        return consequentSet;

    }




}
