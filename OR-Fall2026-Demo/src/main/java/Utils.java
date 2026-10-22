import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Utils {
    /**
     * This is a method that reads in a list of jobs and populates an ArrayList of jobs.
     * @param filename This is the path of csv file with header row "id,procTime,dueDate,weight"
     * @return This method returns a list of jobs
     * @throws FileNotFoundException
     */
    public static ArrayList<Job> readData(String filename) throws FileNotFoundException {
        ArrayList<Job> jobs = (ArrayList<Job>) new CsvToBeanBuilder(new FileReader(filename)).withType(Job.class).build().parse();
        return jobs;
    }
}
