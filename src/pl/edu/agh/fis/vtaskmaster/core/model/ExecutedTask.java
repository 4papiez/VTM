package pl.edu.agh.fis.vtaskmaster.core.model;


public class ExecutedTask {
    private int taskId;
    private long startTime;
    private long endDate;
    private long elapsedTime;
    private boolean done;

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

    public ExecutedTask(int taskId, long startTime, long endDate,
                        long elapsedTime, boolean done) {
        this.taskId = taskId;
        this.startTime = startTime;
        this.endDate = endDate;
        this.elapsedTime = elapsedTime;
        this.done = done;
    }

    @Override
    public String toString() {
        return "ExecutedTask{" +
                "taskId=" + taskId +
                ", startTime=" + startTime +
                ", endDate=" + endDate +
                ", elapsedTime=" + elapsedTime +
                ", done=" + done +
                '}';
    }
}
