package fuzzy.mf.engines;

import java.util.Map;

import fuzzy.mf.Linguistic_Variable.LinguisticVariable;
import fuzzy.mf.rules.RuleBase;

public interface InferenceEngine {
    Map<Double, Double> inference(
        RuleBase ruleBase,
        Map<String, Map<String, Double>> fuzzifiedInputs,
        LinguisticVariable outputVariable
    );
}
