import com.opencsv.bean.CsvBindByName;

public class Job {
    /**
     * id = the unique job number
     */
    @CsvBindByName
    private int id;

    /**
     * procTime = the job's processing time
     */
    @CsvBindByName
    private double procTime;

    /**
     * dueDate = the job's due date
     */
    @CsvBindByName
    private double dueDate;

    /**
     * weight = the job's weight
     */
    @CsvBindByName
    private double weight;

    private double startTime;

    public Job() {
        this.startTime = 1E+10; //default value for jobs yet to be scheduled
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getProcTime() {
        return procTime;
    }

    public void setProcTime(double procTime) {
        this.procTime = procTime;
    }

    public double getDueDate() {
        return dueDate;
    }

    public void setDueDate(double dueDate) {
        this.dueDate = dueDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getStartTime() {
        return startTime;
    }

    /**
     * This is a method that evaluates the completion time of a job (after setting its startTime
     * and procTime).
     * @return double The job's completion time
     */
    public double getCompletionTime() {
        return startTime + procTime;
    }

    /**
     * This is a method that evaluates the lateness of a job (after setting its startTime,
     * procTime, and dueDate).
     * @return double The job's lateness = startTime + procTime - dueDate
     */
    public double getLateness(){
        return startTime + procTime - dueDate;
    }

    /**
     * This is a method that evaluates the tardiness of a job (after setting its startTime,
     * procTime, and dueDate).
     * @return double The job's tardiness = max{Lateness,0}
     */
    public double getTardiness(){
        double val;
        val = getLateness();
        if (val > 0) {
            return val;
        } else {
            return 0;
        }
    }

    /**
     * This is a method that evaluates the weighted completion time of a job (after setting
     * its weight, startTime, procTime).
     * @return double The job's weighted completion time = weight * (completion time)
     */
    public double getWeightedCompletionTime(){
        return weight*getCompletionTime();
    }

    /**
     * This is a method that evaluates the weighted tardiness of a job (after setting
     * its weight, startTime, procTime, and dueDate).
     * @return double The job's weighted completion time = weight * tardiness
     */
    public double getWeightedTardiness(){
        return weight*getTardiness();
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", procTime=" + procTime +
                ", dueDate=" + dueDate +
                ", weight=" + weight +
                ", startTime=" + startTime +
                ", completionTime=" + getCompletionTime() +
                ", tardiness=" + getTardiness() +
                "}";
    }

}
