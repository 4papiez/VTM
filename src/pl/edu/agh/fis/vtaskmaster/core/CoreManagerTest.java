package pl.edu.agh.fis.vtaskmaster.core;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

import static org.junit.Assert.*;


public class CoreManagerTest {
    protected CoreManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new CoreManager("test.db");
        Task task1 = new Task("kupic chleb", "cieply i chrupiacy w Żabce", 1, 500, true, true);
        Task task2 = new Task("sprzedac maslo", "po taniosci", 4, 600, true, false);
        Task task3 = new Task("ukrasc mleko", "z mleczarni", 3, 200, false, true);
        Task task4 = new Task("oddac dlug", "bo wypada", 2, 300, false, false);

        manager.saveTask(task1);
        manager.saveTask(task2);
        manager.saveTask(task3);
        manager.saveTask(task4);

        manager.executeTask(task1, 500);

        manager.finishTask(manager.getAllExecutedTasks().get(0), 5000);
    }

    @After
    public void tearDown() throws Exception {
        manager.finalize();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        Assert.assertTrue("no tasks got from db even after adding", manager.getAllTasks().size() > 0);
    }

    @Test
    public void testGetAllExecutedTasks() throws Exception {

    }

    @Test
    public void testGetTaskByName() throws Exception {

    }

    @Test
    public void testGetFavourites() throws Exception {

    }

    @Test
    public void testGetTodo() throws Exception {

    }

    @Test
    public void testRemoveTaskByName() throws Exception {

    }

    @Test
    public void testSaveTask() throws Exception {

    }

    @Test
    public void testExecuteTask() throws Exception {

    }

    @Test
    public void testUpdateExecutedTask() throws Exception {

    }

    @Test
    public void testFinishTask() throws Exception {

    }

    @Test
    public void testGetHistory() throws Exception {
        Assert.assertTrue(manager.getHistory().size() > 0);
    }
}