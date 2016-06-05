/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.lang.*;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;


/**
 *
 * @author Mateusz Papież
 */
public class CoreDBTest {
    // database instance which has to be initialized before each test case
    private CoreDB db;

    @Before
    public void setUp() {
        db = new CoreDB("test.db");
    }
    
    @After
    public void tearDown() {
        db.clearDB();
        db.closeConnection();
    }

    /**
     * Test of initDB method, of class CoreDB.
     */
    @Test
    public void testInitDB() {
        CoreDB instance = new CoreDB("testInit.db");
        boolean expResult = true;
        boolean result = instance.initDB();

        assertEquals(expResult, result);
    }

    /**
     * Test of addTask method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddTask() throws Exception {
        Task task = new Task("Test", "Testing task.", 1, 1, true, true);
        boolean expResult = true;
        boolean result = db.addTask(task);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeTaskByName method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveTaskByName() throws Exception {
        String taskName = "Test";
        boolean expResult = false;
        db.addTask(new Task(taskName, "", 1, 1, true, true));
        boolean result = db.removeTaskByName(taskName);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateTask method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task ("Test", "Poprawiony", 1, 1, true, true);
        boolean expResult = true;
        db.addTask(new Task("Test", "", 0, 0, false, false));
        task.setName("New name");

        boolean result = db.updateTask(task);

        assertEquals(expResult, result);
    }

    /**
     * Test of updateExecutedTask method, of class CoreDB.
     * @throws java.lang.Exception;
     */
    @Test
    public void testUpdateExecutedTask() throws Exception {
        ExecutedTask etask = new ExecutedTask(1, "Test", 1, 1, 1, true);
        Task task = new Task("Test", "", 1, 1, true, true);
        db.addTask(task);
        db.addExecutedTask(task, 0);
        etask.setElapsedTime(15);
        boolean expResult = true;
        boolean result = db.updateExecutedTask(etask);

        assertEquals(expResult, result);
    }

    /**
     * Test of addExecutedTask method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddExecutedTask() throws Exception {
        Task task = new Task("Test", "Testing", 1, 1, true, true);
        boolean expResult = true;

        db.addTask(task);
        boolean result = db.addExecutedTask(task, 25);

        assertEquals(expResult, result);
    }

    /**
     * Test of getTodo method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTodo() throws Exception {
        ArrayList<Task> expResult = new ArrayList<>(2);
        expResult.add(new Task("Test", "", 1, 1, true, true));
        expResult.add(new Task("Test2", "", 1, 1, true, true));

        db.addTask(new Task("Test", "", 1, 1, true, true));
        db.addTask(new Task("Test2", "", 1, 1, true, true));

        ArrayList<Task> result = db.getTodo();


        assertEquals(expResult.get(0).toString(), result.get(0).toString());
        assertEquals(expResult.get(1).toString(), result.get(1).toString());
    }

    /**
     * Test of getFavourites method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetFavourites() throws Exception {

        ArrayList<Task> expResult = new ArrayList<>(2);
        expResult.add(new Task("Test", "Poprawiony", 1, 1, true, true));
        expResult.add(new Task("", "Poprawiony",1,1,true,true));

        db.addTask(new Task("Test", "Poprawiony", 1, 1, true, true));
        db.addTask(new Task("", "Poprawiony", 1, 1, true, true));
        db.addTask(new Task("Test not fav", "", 1, 1, false, true));

        ArrayList<Task> result = db.getFavourites();

        assertEquals(result.size(), 2);
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
        assertEquals(expResult.get(1).toString(), result.get(1).toString());
    }

    /**
     * Test of getAllTasks method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllTasks() throws Exception {
        ArrayList<Task> expResult = new ArrayList<>(2);
        expResult.add(new Task("Test", "Poprawiony", 1, 1, true, true));
        expResult.add(new Task("","Poprawiony",1,1,true,true));

        db.addTask(new Task("Test","Poprawiony",1,1,true,true));
        db.addTask(new Task("","Poprawiony",1,1,true,true));

        ArrayList<Task> result = db.getAllTasks();

        assertEquals(expResult.get(0).toString(), result.get(0).toString());
        assertEquals(expResult.get(1).toString(), result.get(1).toString());
    }

    /**
     * Test of getHistory method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetHistory() throws Exception {
        ArrayList<Task> expResult = new ArrayList<>(0);
        ArrayList<Task> result = db.getHistory();

        assertEquals(expResult, result);
    }

    /**
     * Test of getTaskByName method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTaskByName() throws Exception {
        String name = "Test";
        Task expResult = new Task("Test", "", 1, 1, true, true);
        db.addTask(expResult);

        Task result = db.getTaskByName(name);

        assertEquals(expResult.toString(), result.toString());
    }


    /**
     * Test of isTaskWithName method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testIsTaskWithName() throws Exception {
        String taskName = "Test";
        boolean expResult = true;

        db.addTask(new Task(taskName, "" ,1, 1, true, true));
        boolean result = db.isTaskWithName(taskName);

        assertEquals(expResult, result);

        result = db.isTaskWithName("Not such task");
        assertEquals(false, result);
    }

    /**
     * Test of getAllExecutedTasks method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllExecutedTasks() throws Exception {
        Task task1 = new Task("Test1", "", 1, 1, true, true);
        Task task2 = new Task("Test2", "", 1, 1, true, true);

        ArrayList<ExecutedTask> expResult = new ArrayList<>(2);
        expResult.add(new ExecutedTask(1, "Test1", 0, 0, 0, false));
        expResult.add(new ExecutedTask(2, "Test2", 0, 0, 0, false));

        db.addTask(task1);
        db.addTask(task2);
        db.addExecutedTask(task1, 0);
        db.addExecutedTask(task2, 0);

        ArrayList<ExecutedTask> result = db.getAllExecutedTasks();

        assertEquals(expResult.get(0).toString(), result.get(0).toString());
        assertEquals(expResult.get(1).toString(), result.get(1).toString());
    }

    /**
     * Test of closeConnection method, of class CoreDB.
     */
    @Test
    public void testCloseConnection() {
        CoreDB instance = new CoreDB("test.db");
        instance.clearDB();
        instance.closeConnection();
    }

    /**
     * Test of clearDB method, of class CoreDB.
     */
    @Test
    public void testClearDB() {
        CoreDB instance = new CoreDB();
        boolean expResult = true;
        boolean result = instance.clearDB();
        assertEquals(expResult, result);
    }

    /**
     * Checks if ExecutedTasks delete when we delete their Task
     */
    @Test
    public void testAfterDeletingTaskExecutedTasksDeleteAsWell()
            throws SQLException
    {
        Task task = new Task("Test", "test", 1, 1, true, true);
        db.addTask(task);
        db.addExecutedTask(task, 0);
        db.addExecutedTask(task, 6);

        assertTrue(db.getAllExecutedTasks().size() == 2);

        db.removeTaskByName("Test");

        assertTrue(db.getAllExecutedTasks().size() == 0);
    }

    @Test
    public void testGetAllExecutedTasksForTaskWithNameReturnsProperExecutedTasks()
        throws SQLException
    {
        Task task1 = new Task("test1", "", 1, 1, true, true);
        Task task2 = new Task("test2", "", 1, 1, true, true);

        db.addTask(task1);
        db.addTask(task2);

        db.addExecutedTask(task1, 16);
        db.addExecutedTask(task1, 1616);

        db.addExecutedTask(task2, 32);
        db.addExecutedTask(task2, 64);

        for (ExecutedTask task : db.getAllExecutedTasksForTaskWithName("test1")) {
            assertEquals(task.getTaskName(), "test1");
        }

        for (ExecutedTask task : db.getAllExecutedTasksForTaskWithName("test2")) {
            assertEquals(task.getTaskName(), "test2");
        }
    }

    @Test
    public void testGetExecutedTasksBeforeDate()
        throws SQLException
    {
        Task task1 = new Task("test1", "", 1, 1, true, true);

        db.addTask(task1);

        db.addExecutedTask(task1, 1463771160000L);
        db.addExecutedTask(task1, 1464548760000L);
        db.addExecutedTask(task1, 1463252760000L);

        for(ExecutedTask task : db.getExecutedTasksBeforeDate(1464548759999L)) {
            assertTrue(task.getStartTime() <= 1464548759999L);
        }

    }

    @Test
    public void testGetExecutedTasksAfterDate()
            throws SQLException
    {
        Task task1 = new Task("test1", "", 1, 1, true, true);

        db.addTask(task1);

        db.addExecutedTask(task1, 1463771160000L);
        db.addExecutedTask(task1, 1464548760000L);
        db.addExecutedTask(task1, 1463252760000L);

        for(ExecutedTask task : db.getExecutedTasksAfterDate(1464548759999L)) {
            assertTrue(task.getStartTime() >= 1464548759999L);
        }

    }

    @Test
    public void testRemoveExecutedTask()
        throws SQLException
    {
        Task task1 = new Task("test1", "", 1, 1, true, true);

        db.addTask(task1);

        db.addExecutedTask(task1, 1463771160000L);
        db.addExecutedTask(task1, 1464548760000L);
        db.addExecutedTask(task1, 1463252760000L);

        assertTrue(db.getAllExecutedTasksForTaskWithName("test1").size() == 3);

        for(ExecutedTask task : db.getAllExecutedTasksForTaskWithName("test1")) {
            db.removeExecutedTask(task);
        }

        assertTrue(db.getAllExecutedTasksForTaskWithName("test1").size() == 0);
    }

    @Test
    public void testGetExecutedTasksDone()
        throws SQLException
    {
        Task task1 = new Task("test1", "", 1, 1, true, true);

        db.addTask(task1);

        db.addExecutedTask(task1, 1463771160000L);
        db.addExecutedTask(task1, 1464548760000L);
        db.addExecutedTask(task1, 1463252760000L);

        ExecutedTask task = db.getExecutedTaskWithId(1);
        assertEquals(task.getId(), 1);

        task.setDone(true);
        db.updateExecutedTask(task);

        task = db.getExecutedTaskWithId(1);
        assertTrue(task.isDone());

        assertEquals(db.getExecutedTasksDone().size(), 1);
        System.out.println(db.getExecutedTasksDone());
    }

    @Test
    public void testGetAllNonFavourites()
        throws SQLException
    {
        String[] name = {"Test1", "Test2", "Test3", "Test4"};
        boolean[] favourite = {false, false, false, true};

        for(int i = 0; i < 4; ++i) {
            Task task = new Task(name[i], "test", 2, 200, favourite[i], false);
            db.addTask(task);
        }

        ArrayList<Task> nonFavourites = db.getNonFavourites();
        for(Task task : nonFavourites) {
            assertFalse(task.isFavourite());
        }

        assertTrue(nonFavourites.size() == 3);
    }
}


