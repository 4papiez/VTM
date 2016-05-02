package pl.edu.agh.fis.vtaskmaster.core.model;

/**
 * Created by Grzegorz on 25.04.2016.
 */
public class ExecutedTask {
    private int taskId;
    private long startTime;
    private long endDate;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public ExecutedTask(int taskId, long startTime, long endDate) {
        this.taskId = taskId;
        this.startTime = startTime;
        this.endDate = endDate;
    }
}
