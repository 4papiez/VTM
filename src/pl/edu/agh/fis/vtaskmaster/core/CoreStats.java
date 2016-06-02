package pl.edu.agh.fis.vtaskmaster.core;


import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

import java.sql.SQLException;
import java.util.ArrayList;

/*
Statistics provider for TaskMaster.
 */
public class CoreStats {

    /**
    Instance of CoreDB. It's the one which CoreManager has
    and the registration of it have place in his constructor.
     */
    private CoreDB db;

    public CoreStats(CoreDB db) {
        this.db = db;
    }

    /**
    Enum for {@link #efficiency(TasksFilter)} method.
     */
    public enum TasksFilter {
        ALL, FAVOURITES, NON_FAVOURITES
    }

    /**
     * Returns efficiency of the user.
     *
     * @param filter determines if the efficiency is calculated for favourites, non-favourites or all
     * @return efficiency
     * @throws SQLException
     */
    public double efficiency(TasksFilter filter) throws SQLException {
        ArrayList<Task> tasks;

        switch(filter) {
            case ALL:
                tasks = db.getAllTasks();
                break;
            case FAVOURITES:
                tasks = db.getFavourites();
                break;
            case NON_FAVOURITES:
                tasks = db.getNonFavourites();
                break;
            default:
                tasks = null;
                break;
        }

        double numerator = 0;
        double denominator = 0;

        for(Task task : tasks) {
            float priority = (float)task.getPriority();
            float expectedTime = (float)task.getExpectedTime();

            for(ExecutedTask executedTask : db.getAllExecutedTasksForTaskWithName(task.getName())){
                if (!executedTask.isDone()) continue; // only done tasks
                float elapsedTime = (float)executedTask.getElapsedTime();

                if(expectedTime > elapsedTime) {
                    numerator += (expectedTime - elapsedTime) / expectedTime * priority;
                } else {
                    numerator += 2 * (expectedTime - elapsedTime) / expectedTime * priority;
                }
                denominator += priority;
            }
        }

        if(denominator == 0)
            return 0;

        return numerator / denominator;
    }

    /**
     * Calculates percentage value of tasks done on time.
     * @return percentage of tasks done on time
     * @throws SQLException
     */
    public float onTime() throws SQLException{
        int onTime = 0;
        int allDoneTasks = 0;

        for(Task task : db.getAllTasks()) {
            long expectedTime = task.getExpectedTime();

            for(ExecutedTask executedTask : db.getAllExecutedTasksForTaskWithName(task.getName())) {
                if(!executedTask.isDone()) continue;

                allDoneTasks++;
                if(expectedTime > executedTask.getElapsedTime())
                    onTime++;
            }
        }

        return (float) onTime / (float) allDoneTasks * 100;
    }

    /**
     * Calculates average difference between expectedTime and elapsedTime for all done tasks.
     * It can be calculated as an absolute value or percentage value, what is determined by the passed flag.
     * @param absolute flag telling if the average should be absolute or percentage
     * @return average difference between expectedTime and elapsedTime for all done tasks
     * @throws SQLException
     */
    public long averageDifference(boolean absolute)
        throws SQLException
    {
        float sum = 0;
        float counter = 0;

        for(Task task : db.getAllTasks()) {
            float expectedTime = (float)task.getExpectedTime();
            for(ExecutedTask executedTask : db.getAllExecutedTasksForTaskWithName(task.getName())) {
                if(!executedTask.isDone()) continue;

                float elapsedTime = (float)executedTask.getElapsedTime();

                if(absolute) {
                    sum += expectedTime - elapsedTime;
                } else {
                    sum += (expectedTime - elapsedTime) / expectedTime;
                }
                counter++;
            }
        }

        if(absolute) {
            return (long) (sum / counter);
        } else {
            return (long) (sum / counter * 100);
        }
    }

    /**
     * @return number of done tasks
     * @throws SQLException
     */
    public int numberOfDoneTasks()
        throws SQLException
    {
        int counter = 0;

        for(ExecutedTask executedTask : db.getAllExecutedTasks()) {
                if(executedTask.isDone())
                    counter++;
        }

        return counter;
    }

    /**
     * @return total time spent working
     * @throws SQLException
     */
    public long timeSpentWorking()
        throws SQLException
    {
        long totalTime = 0;
        for(ExecutedTask executedTask : db.getAllExecutedTasks())
            totalTime += executedTask.getElapsedTime();

        return totalTime;
    }

    /**
     * @return average priority of done tasks
     * @throws SQLException
     */
    public int averagePriority() throws SQLException {
        int sum = 0;
        int counter = 0;
        for(ExecutedTask executedTask : db.getExecutedTasksDone()) {
            int priority = db.getTaskByName(executedTask.getTaskName())
                    .getPriority();

            sum += priority;
            counter++;
        }

        return sum / counter;
    }

    public long averageTimeForTaskWithName(String taskName)
        throws SQLException
    {
        if(!db.isTaskWithName(taskName)) {
            return 0;
        }

        long sum = 0;
        int doneCounter = 0;
        for(ExecutedTask executedTask : db.getAllExecutedTasksForTaskWithName(taskName)) {
            if(!executedTask.isDone()) continue;
            sum += executedTask.getElapsedTime();
            doneCounter++;
        }

        if(doneCounter == 0)
            return 0;

        return sum / doneCounter;
    }

}
