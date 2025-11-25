package fuzzy.mf.rules;

import java.util.ArrayList;
import java.util.List;

public class RuleBase {

    private List<FuzzyRule> rules = new ArrayList<>();

    public void addRule(FuzzyRule rule) {
        rules.add(rule);
    }

    public List<FuzzyRule> getRules() {
        return rules;
    }
    public RuleBuilder addRule() {
        return new RuleBuilder(this);
    }
}
