package fuzzy.mf.rules;

import java.util.HashMap;
import java.util.Map;

public class RuleBuilder {
    private Map<String,String> antecedents = new HashMap<>();
    boolean selectedOp;
    private RuleBase base;


    RuleBuilder(RuleBase base){
        this.base = base;
    }

    public RuleBuilder ifVar(String var, String set){
        antecedents.put(var, set);
        return this;
    }


    public RuleBuilder and(String var, String set){
        antecedents.put(var, set);
        selectedOp=true;
        return this;
    }
    public RuleBuilder or(String var, String set){
        antecedents.put(var, set);
        selectedOp=false;
        return this;
    }


    public void then(String var, String set){
        FuzzyRule rule;

        if(selectedOp){
             rule = new FuzzyRule(new HashMap<>(antecedents), var, set, AndRulesOp.MIN,null);
        }
        else{
             rule = new FuzzyRule(new HashMap<>(antecedents), var, set, null,OrRulesOp.MAX);
        }

        base.addRule(rule);
        antecedents.clear();
    }
}
