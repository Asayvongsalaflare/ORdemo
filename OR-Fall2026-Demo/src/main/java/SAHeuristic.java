import java.util.ArrayList;
import java.util.Random;

/**
 * Simulated Annealing heuristic for the Job Sequencing problem.
 * Objective: minimize Total Weighted Tardiness (TWT).
 */
public class SAHeuristic {

    /** Shared RNG for the entire run. */
    private static final Random RAND = new Random();

    /**
     * Run simulated annealing on the given jobs.
     *
     * @param jobs         input jobs in the initial order to start from
     * @param max_iter     number of SA iterations
     * @param initial_temp starting temperature t0 (> 0)
     * @param alpha        cooling factor in (0, 1]
     * @return the final trial Solution after max_iter iterations
     */
    public static Solution run(ArrayList<Job> jobs,
                               int max_iter,
                               double initial_temp,
                               double alpha) {

        int n = jobs.size();
        if (n < 2) {
            return new Solution(jobs);   // nothing to swap
        }

        // Start from the order supplied in `jobs`.
        Solution current = new Solution(jobs);
        double currentTWT = current.evaluateTotalWeightedTardiness();

        double temperature = initial_temp;

        for (int k = 0; k < max_iter; k++) {

            // (a) Copy the current solution into a candidate.
            //     Uses your existing copy constructor Solution(Solution).
            Solution candidate = new Solution(current);

            // (b) Pick two distinct random positions.
            int p1 = RAND.nextInt(n);
            int p2;
            do {
                p2 = RAND.nextInt(n);
            } while (p2 == p1);

            // (c) Swap in the candidate.
            candidate.SwapJobs(p1, p2);

            // (d) Evaluate the candidate.
            double candidateTWT = candidate.evaluateTotalWeightedTardiness();

            // (e)/(f) Metropolis acceptance rule.
            boolean accept;
            if (candidateTWT <= currentTWT) {
                accept = true;
            } else {
                double probability =
                        Math.exp((currentTWT - candidateTWT) / temperature);
                accept = RAND.nextDouble() < probability;
            }

            if (accept) {
                current = candidate;
                currentTWT = candidateTWT;
            }

            // (g) Cool the temperature.
            temperature *= alpha;
        }

        return current;
    }

    /**
     * Problem 3 driver. Reads JobsData-40.csv, builds the identity-order
     * solution (jobs 1..40), sets initial_temp = that TWT, runs SA with
     * max_iter=10000 and alpha=0.99, and prints the result.
     */
    public static void main(String[] args) {

        // Use YOUR existing CSV reader from the in-class project.
        // Replace this line with whatever call your project already uses,
        // e.g.: ArrayList<Job> jobs = Job.readJobs("JobsData-40.csv");
        ArrayList<Job> jobs = readJobs("JobsData-40.csv");

        // Ensure the initial order is 1, 2, ..., 40 as the assignment requires.
        // If the CSV is already in that order this line is a no-op.
        jobs.sort((a, b) -> Integer.compare(a.getId(), b.getId()));

        // Build the identity-order initial solution and record its TWT.
        Solution initial = new Solution(jobs);
        double initialTWT = initial.evaluateTotalWeightedTardiness();
        System.out.println("Initial TWT (jobs 1..40): " + initialTWT);

        // Run SA with the mandated parameters.
        Solution result = run(jobs, 10000, initialTWT, 0.99);

        // Required output for Problem 3.
        System.out.println("=====================================");
        System.out.println("FINAL SEQUENCE AND TWT:");
        result.printAbbrv();                    // prints: Solution: <ids>; TWT=...
        System.out.println("=====================================");
    }

    /**
     * Minimal CSV reader.
     *
     * If your in-class project ALREADY has a CSV reader (it should — you
     * used it for the neighborhood search), delete this method and call
     * that reader from main() instead. This is only here so the file
     * compiles standalone.
     *
     * Expected CSV: id,procTime,dueDate,weight  (one job per row, optional header).
     *
     * <-- ADAPT the four parse + new Job(...) lines to match your Job class.
     */
    private static ArrayList<Job> readJobs(String filename) {
        ArrayList<Job> jobs = new ArrayList<>();
        try (java.io.BufferedReader br =
                     new java.io.BufferedReader(new java.io.FileReader(filename))) {

            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (!headerSkipped && !Character.isDigit(line.charAt(0))) {
                    headerSkipped = true;
                    continue;
                }
                String[] parts = line.split(",");

                // *** ADAPT THESE FOUR LINES TO YOUR JOB CLASS ***
                int    id       = Integer.parseInt(parts[0].trim());
                double procTime = Double.parseDouble(parts[1].trim());
                double dueDate  = Double.parseDouble(parts[2].trim());
                double weight   = Double.parseDouble(parts[3].trim());
                jobs.add(new Job(id, procTime, dueDate, weight));
                // *** ADAPT THE LINE ABOVE TO YOUR JOB CONSTRUCTOR ***
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Could not read " + filename, e);
        }
        return jobs;
    }
}