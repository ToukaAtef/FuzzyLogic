package fuzzy.mf.defuzzifiers;

import java.util.Map;

public interface Defuzzifier {
    double defuzzify(Map<Double, Double> aggregatedOutput);
}
