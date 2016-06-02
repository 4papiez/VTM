package pl.edu.agh.fis.vtaskmaster.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class CoreStatsTest {
    private CoreDB db;
    private CoreStats stats;

    @Before
    public void setUp() {
        db = new CoreDB("test.db");
        stats = new CoreStats(db);
    }

    @After
    public void tearDown() {
        db.clearDB();
        db.closeConnection();
    }

    @Test
    public void testEfficiencyForAllTasksWhenNoTasksInDBReturnsZero()
            throws SQLException
    {
        assertEquals(stats.efficiency(CoreStats.TasksFilter.ALL), 0, 0.01);
    }

    @Test
    public void testEfficiencyForAllTasksWhenTasksInDBReturnProperValue()
        throws SQLException
    {
        Task task1 = new Task("Task1", "test", 9, 500, false, false);
        Task task2 = new Task("Task2", "test", 5, 1000, false, false);

        db.addTask(task1);
        db.addTask(task2);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task2, 0);
        db.addExecutedTask(task2, 0);

        ExecutedTask executedTask1 = db.getAllExecutedTasksForTaskWithName("Task1").get(0);
        ExecutedTask executedTask2 = db.getAllExecutedTasksForTaskWithName("Task1").get(1);

        ExecutedTask executedTask3 = db.getAllExecutedTasksForTaskWithName("Task2").get(0);
        ExecutedTask executedTask4 = db.getAllExecutedTasksForTaskWithName("Task2").get(1);

        executedTask1.setDone(true);
        executedTask1.setElapsedTime(200);

        executedTask2.setDone(true);
        executedTask2.setElapsedTime(450);

        executedTask3.setDone(true);
        executedTask3.setElapsedTime(2000);

        executedTask4.setDone(true);
        executedTask4.setElapsedTime(700);

        db.updateExecutedTask(executedTask1);
        db.updateExecutedTask(executedTask2);
        db.updateExecutedTask(executedTask3);
        db.updateExecutedTask(executedTask4);

        assertEquals(
                stats.efficiency(CoreStats.TasksFilter.ALL),
                -0.0785714286,
                0.001
        );
    }

    @Test
    public void testEfficiencyForFavTasksWhenTasksInDBReturnProperValue()
            throws SQLException
    {
        Task task1 = new Task("Task1", "test", 9, 500, true, false);
        Task task2 = new Task("Task2", "test", 5, 1000, true, false);

        db.addTask(task1);
        db.addTask(task2);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task2, 0);
        db.addExecutedTask(task2, 0);

        ExecutedTask executedTask1 = db.getAllExecutedTasksForTaskWithName("Task1").get(0);
        ExecutedTask executedTask2 = db.getAllExecutedTasksForTaskWithName("Task1").get(1);

        ExecutedTask executedTask3 = db.getAllExecutedTasksForTaskWithName("Task2").get(0);
        ExecutedTask executedTask4 = db.getAllExecutedTasksForTaskWithName("Task2").get(1);

        executedTask1.setDone(true);
        executedTask1.setElapsedTime(200);

        executedTask2.setDone(true);
        executedTask2.setElapsedTime(450);

        executedTask3.setDone(true);
        executedTask3.setElapsedTime(2000);

        executedTask4.setDone(true);
        executedTask4.setElapsedTime(700);

        db.updateExecutedTask(executedTask1);
        db.updateExecutedTask(executedTask2);
        db.updateExecutedTask(executedTask3);
        db.updateExecutedTask(executedTask4);

        assertEquals(
                stats.efficiency(CoreStats.TasksFilter.FAVOURITES),
                -0.0785714286,
                0.001
        );
    }

    @Test
    public void testEfficiencyForNonFavTasksWhenTasksInDBReturnProperValue()
            throws SQLException
    {
        Task task1 = new Task("Task1", "test", 9, 500, false, false);
        Task task2 = new Task("Task2", "test", 5, 1000, false, false);
        Task task3 = new Task("Task3", "test", 10, 1000, true, false);

        db.addTask(task1);
        db.addTask(task2);
        db.addTask(task3);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task2, 0);
        db.addExecutedTask(task2, 0);
        db.addExecutedTask(task3, 0);

        ExecutedTask executedTask1 = db.getAllExecutedTasksForTaskWithName("Task1").get(0);
        ExecutedTask executedTask2 = db.getAllExecutedTasksForTaskWithName("Task1").get(1);

        ExecutedTask executedTask3 = db.getAllExecutedTasksForTaskWithName("Task2").get(0);
        ExecutedTask executedTask4 = db.getAllExecutedTasksForTaskWithName("Task2").get(1);

        ExecutedTask executedTask5 = db.getAllExecutedTasksForTaskWithName("Task3").get(0);

        executedTask1.setDone(true);
        executedTask1.setElapsedTime(200);

        executedTask2.setDone(true);
        executedTask2.setElapsedTime(450);

        executedTask3.setDone(true);
        executedTask3.setElapsedTime(2000);

        executedTask4.setDone(true);
        executedTask4.setElapsedTime(700);

        executedTask5.setDone(true);
        executedTask5.setElapsedTime(700);

        db.updateExecutedTask(executedTask1);
        db.updateExecutedTask(executedTask2);
        db.updateExecutedTask(executedTask3);
        db.updateExecutedTask(executedTask4);
        db.updateExecutedTask(executedTask5);

        assertEquals(
                stats.efficiency(CoreStats.TasksFilter.NON_FAVOURITES),
                -0.0785714286,
                0.001
        );
    }

    @Test
    public void testOnTime()
    throws SQLException
    {
        Task task1 = new Task("Task1", "test", 9, 500, true, false);
        Task task2 = new Task("Task2", "test", 5, 1000, true, false);

        db.addTask(task1);
        db.addTask(task2);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task2, 0);
        db.addExecutedTask(task2, 0);

        ExecutedTask executedTask1 = db.getAllExecutedTasksForTaskWithName("Task1").get(0);
        ExecutedTask executedTask2 = db.getAllExecutedTasksForTaskWithName("Task1").get(1);

        ExecutedTask executedTask3 = db.getAllExecutedTasksForTaskWithName("Task2").get(0);
        ExecutedTask executedTask4 = db.getAllExecutedTasksForTaskWithName("Task2").get(1);

        executedTask1.setDone(true);
        executedTask1.setElapsedTime(200);

        executedTask2.setDone(true);
        executedTask2.setElapsedTime(900);

        executedTask3.setDone(true);
        executedTask3.setElapsedTime(2000);

        executedTask4.setDone(true);
        executedTask4.setElapsedTime(700);

        db.updateExecutedTask(executedTask1);
        db.updateExecutedTask(executedTask2);
        db.updateExecutedTask(executedTask3);
        db.updateExecutedTask(executedTask4);

        assertEquals(stats.onTime(), 50, 0.01);
    }

    @Test
    public void testAverageDifferenceAbsolute()
        throws SQLException
    {
        Task task1 = new Task("Task1", "test", 9, 500, true, false);

        db.addTask(task1);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);

        ExecutedTask executedTask1 = db.getAllExecutedTasksForTaskWithName("Task1").get(0);
        ExecutedTask executedTask2 = db.getAllExecutedTasksForTaskWithName("Task1").get(1);

        executedTask1.setDone(true);
        executedTask1.setElapsedTime(400);

        executedTask2.setDone(true);
        executedTask2.setElapsedTime(800);

        db.updateExecutedTask(executedTask1);
        db.updateExecutedTask(executedTask2);

        assertEquals(-100, stats.averageDifference(true));
    }

    @Test
    public void testAverageDifferencePercentage()
            throws SQLException
    {
        Task task1 = new Task("Task1", "test", 9, 500, true, false);

        db.addTask(task1);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);

        ExecutedTask executedTask1 = db.getAllExecutedTasksForTaskWithName("Task1").get(0);
        ExecutedTask executedTask2 = db.getAllExecutedTasksForTaskWithName("Task1").get(1);

        executedTask1.setDone(true);
        executedTask1.setElapsedTime(400);

        executedTask2.setDone(true);
        executedTask2.setElapsedTime(800);

        db.updateExecutedTask(executedTask1);
        db.updateExecutedTask(executedTask2);

        assertEquals(-20, stats.averageDifference(false));
    }

    @Test
    public void testAveragePriority()
        throws SQLException
    {
        Task task1 = new Task("Task1", "test", 10, 500, true, false);
        Task task2 = new Task("Task2", "test", 4, 1000, true, false);

        db.addTask(task1);
        db.addTask(task2);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task2, 0);
        db.addExecutedTask(task2, 0);
        db.addExecutedTask(task2, 0);

        ExecutedTask executedTask1 = db.getAllExecutedTasksForTaskWithName("Task1").get(0);
        ExecutedTask executedTask2 = db.getAllExecutedTasksForTaskWithName("Task1").get(1);

        ExecutedTask executedTask3 = db.getAllExecutedTasksForTaskWithName("Task2").get(0);
        ExecutedTask executedTask4 = db.getAllExecutedTasksForTaskWithName("Task2").get(1);
        ExecutedTask executedTask5 = db.getAllExecutedTasksForTaskWithName("Task2").get(2);

        executedTask1.setDone(true);
        executedTask2.setDone(true);
        executedTask3.setDone(true);
        executedTask4.setDone(true);
        executedTask5.setDone(true);

        db.updateExecutedTask(executedTask1);
        db.updateExecutedTask(executedTask2);
        db.updateExecutedTask(executedTask3);
        db.updateExecutedTask(executedTask4);
        db.updateExecutedTask(executedTask5);

        assertEquals(6, stats.averagePriority());
    }

    @Test
    public void testAverageTimeOfTaskWithName()
        throws SQLException
    {
        Task task1 = new Task("Task1", "test", 10, 500, true, false);
        db.addTask(task1);

        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task1, 0);

        long time = 3000L;
        for(ExecutedTask executedTask : db.getAllExecutedTasksForTaskWithName("Task1")) {
            executedTask.setDone(true);
            executedTask.setElapsedTime(time);
            db.updateExecutedTask(executedTask);
            time += 1000L;
        }

        assertEquals(4500, stats.averageTimeForTaskWithName("Task1"));
    }

}
