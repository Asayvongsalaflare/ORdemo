import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NSHeuristic{
    public static final double EPSILON = 1E-10;

    public static void main(String[] args) throws FileNotFoundException {
        //read job data
        ArrayList<Job> jobData = Utils.readData("data/JobsData-40.csv");

        //runIteration neighborhood search heuristic
        Solution nsSolution = run(jobData, 200); // yields 161k w/ move operator, stops after 6 iterations

        //print solution and TWT
        System.out.println("===NS SOLUTION===");
        nsSolution.printAbbrv();
    }
    public static Solution run(ArrayList<Job> jobData, int max_iter) {
        //Define ArrayLists to hold job sequences
        Solution currentSol = new Solution(jobData);
        //ArrayList<Job> currentSol = new ArrayList<Job> (jobData); //stores current sol
        Solution bestCandidateSol = new Solution(jobData);
        //ArrayList<Job> bestCandidateSol = new ArrayList<Job> (jobData); //stores best candidate sol within an iteration

        //Keep record of best objective value
        double bestValue = currentSol.evaluateTotalWeightedTardiness();
        //double bestValue = Utils.evaluateTotalWeightedTardiness(jobData); //initialize with TWT of input solution

        //Other variables used within the heuristic
        double val; //stores TWT of neighborhood solutions
        int n = jobData.size(); //number of jobs
        boolean iterationYieldsImprovement = true; //indicates whether an improvement is made within an NS iteration
        int iter = 0; //Counter for the number of NS iterations completed

        /*
        Loop to perform at most max_iter iterations; terminate if an iteration is
        performed with no improvement
         */
        while (iterationYieldsImprovement && iter < max_iter) {
            iterationYieldsImprovement = false;
            /*
            In each iteration, check all solutions that can be obtained by removing one job and
            inserting it in a different position
             */
            for (int i1 = 0; i1 < n; i1++){
                for (int i2 = 0; i2 < n; i2++){
                    //try removing job in position i1 and inserting at position i2
                    Solution candidateSol = new Solution(currentSol.getJobSeq());
                    //ArrayList<Job> candidateSol = new ArrayList<Job> (currentSol); //new candidate solution
                    candidateSol.moveJob(i1,i2);
                    //Utils.moveJob(candidateSol, i1, i2);
                    //Utils.printSolAbbrv(candidateSol);
                    val = candidateSol.evaluateTotalWeightedTardiness();
                    //val = Utils.evaluateTotalWeightedTardiness(candidateSol);
                    //check whether this solution improves upon the incumbent
                    if (val < bestValue - EPSILON) {
                        bestValue = val;
                        bestCandidateSol = candidateSol;
                        iterationYieldsImprovement = true;
                    }
                }
            }
            //update currentSol
            if (iterationYieldsImprovement) {
                currentSol = bestCandidateSol;
            }
            bestValue = currentSol.evaluateTotalWeightedTardiness();
            //bestValue = Utils.evaluateTotalWeightedTardiness(currentSol); //currentValue;
            iter++;
        }

        //print number of iterations
        System.out.println();
        System.out.println("NS terminated after " + iter + " iterations");

        return currentSol;
    }
}
