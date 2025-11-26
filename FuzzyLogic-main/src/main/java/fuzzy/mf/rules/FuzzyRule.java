package fuzzy.mf.rules;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FuzzyRule {
    Map<String, String> antecedents;
    String consequentVariable;
    String consequentSet;
    boolean setOp;



    public FuzzyRule(Map<String, String> antecedents, String consequentVariable, String consequentSet,boolean setOp) {
        this.antecedents = antecedents;
        this.consequentVariable = consequentVariable;
        this.consequentSet = consequentSet;
        this.setOp=setOp;


    }

    public double applyRule(Map<String, Map<String, Double>> fuzzifiedInputs){

        double res=setOp?1.0:0.0;
//        System.out.println(antecedents);
        for(Map.Entry<String,String> entry: this.antecedents.entrySet()){
            String antecedent = entry.getKey(); // Experiance
            String consequent = entry.getValue(); // High
            double value=fuzzifiedInputs.get(antecedent).get(consequent); // 0.8
            if(setOp){

                    res = Math.min(res, value);

            } else {

                    res = Math.max(res, value);

            }


        }
        return res;
    }


    public String getConsequentSet() {
        return consequentSet;

    }




}
