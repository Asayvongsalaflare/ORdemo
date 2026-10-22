import java.util.Comparator;

public class TwtComparator implements Comparator<Solution> {
    public static final double EPSILON = 1E-10;

    @Override
    public int compare(Solution s1, Solution s2){
        double val = s1.evaluateTotalWeightedTardiness() - s2.evaluateTotalWeightedTardiness();
        if (val < -EPSILON) {
            return -1;
        } else if (val > EPSILON) {
            return 1;
        } else {
            return 0;
        }
    }
}