import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Demo {
    public static void main(String[] args) throws FileNotFoundException {
        //declare job j1 with processing time = 5, due date = 25, late penalty = 19
        Job j1 = new Job();
        j1.setId(1);
        j1.setProcTime(5);
        j1.setDueDate(25);
        j1.setWeight(19);

        //declare job j2 with processing time = 20, due date = 22, late penalty = 12
        Job j2 = new Job();
        j2.setId(2);
        j2.setProcTime(20);
        j2.setDueDate(22);
        j2.setWeight(12);

        //declare job j3 with processing time = 15, due date = 35, late penalty = 34
        Job j3 = new Job();
        j3.setId(3);
        j3.setProcTime(15);
        j3.setDueDate(35);
        j3.setWeight(34);

        //print information about the jobs
        System.out.println("PRINT JOB INFO");
        System.out.println(j1.toString());
        System.out.println(j2.toString());

        //store a job sequence as a Solution object
        ArrayList<Job> jobSequence = new ArrayList<Job>();
        jobSequence.add(j1);
        jobSequence.add(j2);
        jobSequence.add(j3);
        Solution sol = new Solution(jobSequence);

        //print the job sequence in elongated form
        System.out.println("PRINT JOB SEQUENCE");
        sol.print();
        //print the job sequence in abbreviated form
        sol.printAbbrv();

        //obtain the total weighted tardiness of the job sequence
        System.out.println("TWT = " + sol.evaluateTotalWeightedTardiness());

        //read a list of jobs in from csv and define an initial job sequence
        ArrayList<Job> jobDataSmall = Utils.readData("data/JobsData-small.csv");
        Solution sol2 = new Solution(jobDataSmall);
        //print the job sequence in abbreviated form
        System.out.println("PRINT JOB SEQUENCE FROM CSV FILE");
        sol2.printAbbrv();

        //Make some changes to the job sequence and see if they improve the solution...
        //Note: Positions are numbered 0,1,...,nJobs-1

        //Move the job at position 4 to position 0
        sol2.moveJob(4,0);
        sol2.printAbbrv();

        //Then move the job at position 1 to position 3 accumalted from previous moveJob
        sol2.moveJob(1,3);
        sol2.printAbbrv();
        //Then reverse the subsequence beginning at position 2 and ending at position 4
        sol2.reverseSubsequence(2,4);
        sol2.printAbbrv();

        //Then reverse the subsequence beginning at position 0 and ending at position 3 (doing 3 first this time)
        sol2.reverseSubsequence(3,0);
        sol2.printAbbrv();

        //Randomly generate two integers between 0,1,...,nJobs-1; remove the job at one integer's position
        //and insert at the other's
        Random r = new Random(5); //seed makes output deterministic
        int nJobs = sol2.getSize();
        int position1, position2;
        System.out.println("random move:");
        position1 = r.nextInt(nJobs); // generates a random integer in {0,1,...,nJobs -1}
        position2 = r.nextInt(nJobs);
        System.out.println("int1 =" + position1 + " int2 =" + position2);
        sol2.moveJob(position1,position2);
        sol2.printAbbrv();
        //Do a couple more random moves code below also accumalates
        System.out.println("another random move:");
        position1 = r.nextInt(nJobs); // generates a random integer in {0,1,...,nJobs -1}
        position2 = r.nextInt(nJobs);
        System.out.println("int1 =" + position1 + " int2 =" + position2);
        sol2.moveJob(position1,position2);
        sol2.printAbbrv();

        System.out.println("one more random move:");
        position1 = r.nextInt(nJobs); // generates a random integer in {0,1,...,nJobs -1}
        position2 = r.nextInt(nJobs);
        System.out.println("int1 =" + position1 + " int2 =" + position2);
        sol2.moveJob(position1,position2);
        sol2.printAbbrv();
    }
}
