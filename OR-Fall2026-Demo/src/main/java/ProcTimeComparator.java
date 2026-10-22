import java.util.Comparator;

public class ProcTimeComparator implements Comparator<Job> {
    public static final double EPSILON = 1E-10;

    @Override
    public int compare(Job j1, Job j2){
        double val = j1.getProcTime() - j2.getProcTime();
        if (val < -EPSILON) {
            return -1;
        } else if (val > EPSILON) {
            return 1;
        } else {
            return 0;
        }
    }
}