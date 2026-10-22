/**
 * This program reads in an initial job sequence from a CSV file and then reorders
 * the jobs in order of increasing processing time.
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
public class SPTHeuristic {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Running SPT Heuristic:");
        ArrayList<Job> jobData = Utils.readData("data/JobsData.csv");
        Solution sptSolution = run(jobData);
        System.out.println("===SPT SOLUTION===");
        sptSolution.printAbbrv();
    }
    public static Solution run(ArrayList<Job> jobData) {
        //Declare ArrayList<Job> to record best solution in a neighborhood
        ArrayList<Job> sptSeq = new ArrayList<>(jobData);

        //sort the job data by processing time
        Collections.sort(sptSeq, new ProcTimeComparator());

        //define a new solution using the sorted job sequence (and return it)
        return new Solution(sptSeq);
    }
}
