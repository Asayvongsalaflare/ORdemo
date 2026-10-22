import java.util.ArrayList;

public class Solution {
    /**
     * seq = an ArrayList of Jobs that defines a job sequence
     */
    private ArrayList<Job> seq;

    public Solution(ArrayList<Job> jobData) {
        this.seq = new ArrayList<Job>(jobData);
    }

    public Solution(Solution solution) {
        this.seq = new ArrayList<Job> (solution.getJobSeq());
    }

    public int getSize(){
        return this.seq.size();
    }

    public ArrayList<Job> getJobSeq(){
        return this.seq;
    }


    /**
     * This is a method that evaluates the total weighted tardiness of the solution.
     * @return double This returns the total weighted tardiness of the solution's job sequence
     */
    public double evaluateTotalWeightedTardiness() {
        double currentTime = 0; // update as you move through the list
        double twt = 0;
        for(int i = 0; i < this.seq.size(); i++) {
            this.seq.get(i).setStartTime(currentTime);
            twt += this.seq.get(i).getWeightedTardiness();
            currentTime += this.seq.get(i).getProcTime();
        }
        return twt;
    }

    /**
     * This is a method that removes a job from a job sequence and reinserts
     * it in a different position.
     * @param fromPosition This is the position of the job to be moved
     * @param toPosition = This is the position where job should be inserted
     */
    public void moveJob(int fromPosition, int toPosition) {
        //move job from position "fromPosition" into position "toPosition"
        Job j1 = this.seq.get(fromPosition);
        Job j2 = this.seq.get(toPosition);
        this.seq.remove(fromPosition);
        this.seq.add(toPosition,j1);
    }

    /**
     * This is a method that reverses the subsequence of jobs between two positions in job sequence
     * @param position1 This is the position of the first job in the subsequence to be reversed
     * @param position2 This is the position of the lsat job in the subsequence to be reversed
     */
    public void reverseSubsequence(int position1, int position2) {
        int min_pos = Math.min(position1, position2);
        int max_pos = Math.max(position1, position2);
        ArrayList<Job> temp = new ArrayList<Job>();
        for (int i = max_pos; i >= min_pos; i--){
            temp.add(this.seq.get(i));
        }
        int count = 0;
        for (int i = min_pos; i <= max_pos; i++){
            this.seq.set(i,temp.get(count));
            count++;
        }
    }

    /**
     * This is a method that prints a job sequence and its
     * total weighted tardiness.
     */
    public void print() {
        double twt = this.evaluateTotalWeightedTardiness();
        System.out.println("Solution: ");
        for(int i = 0; i < this.seq.size(); i++) {
            System.out.println(this.seq.get(i).toString());
        }
        System.out.println("Total Weighted Tardiness: " + twt);
    }

    /**
     * This is a method that prints a job sequence in compact form.
     */
    public void printAbbrv() {
        double twt = this.evaluateTotalWeightedTardiness();
        System.out.print("Solution:");
        for(int i = 0; i < this.seq.size(); i++) {
            System.out.print(" " + this.seq.get(i).getId());
        }
        System.out.println("; TWT=" + twt);
    }
}
