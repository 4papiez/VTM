package pl.edu.agh.fis.vtaskmaster.core.model;


public class ExecutedTask {
    private String taskName;
    private long startTime;
    private long endTime;
    private long elapsedTime;
    private boolean done;

    public String getTaskName() {
        return taskName;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public ExecutedTask(String taskName, long startTime, long endTime,
                        long elapsedTime, boolean done) {
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.elapsedTime = elapsedTime;
        this.done = done;
    }

    @Override
    public String toString() {
        return "ExecutedTask{" +
                "taskName=" + taskName +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", elapsedTime=" + elapsedTime +
                ", done=" + done +
                '}';
    }
}
