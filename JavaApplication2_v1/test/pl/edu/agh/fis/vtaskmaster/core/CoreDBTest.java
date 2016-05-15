/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster.core;

import java.util.ArrayList;
import java.lang.*;
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
    
    public CoreDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initDB method, of class CoreDB.
     */
    @Test
    public void testInitDB() {
        System.out.println("initDB");
        CoreDB instance = new CoreDB();
        boolean expResult = true;
        boolean result = instance.initDB();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of addTask method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddTask() throws Exception {
        System.out.println("addTask");
        Task task = new Task("Test","Testing task.",1,1,true,true);
        CoreDB instance = new CoreDB();
        boolean expResult = true;
        boolean result = instance.addTask(task);
        assertEquals(expResult, result);
        result = instance.removeTaskByName("Test");
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of removeTaskByName method, of class CoreDB.
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveTaskByName() throws Exception {
        System.out.println("removeTaskByName");
        String taskName = "";
        CoreDB instance = new CoreDB();
        boolean expResult = false;
        boolean result = instance.addTask(new Task("","",1,1,true,true));
        result = instance.removeTaskByName(taskName);
        assertEquals(expResult, result);
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of updateTask method, of class CoreDB.
     * throws java.lang.Exception
     */
    @Test
    public void testUpdateTask() throws Exception {
        System.out.println("updateTask");
        Task task = new Task ("Test","Poprawiony",1,1,true,true);
        CoreDB instance = new CoreDB();
        boolean expResult = true;
        boolean result = instance.addTask(new Task("Test","",0,0,false,false));
        result = instance.updateTask(task);
        assertEquals(expResult, result);
        result = instance.removeTaskByName("Test");
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of updateExecutedTask method, of class CoreDB.
     * throws java.lang.Exception;
     */
    @Test
    public void testUpdateExecutedTask() throws Exception {
        fail("cos");
        System.out.println("updateExecutedTask");
        ExecutedTask task = new ExecutedTask(1,"Test",1,1,1,true);
        CoreDB instance = new CoreDB();
        boolean expResult = true;
        boolean result = instance.updateExecutedTask(task);
        assertEquals(expResult, result);
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
        
    }

    /**
     * Test of addExecutedTask method, of class CoreDB.
     */
    @Test
    public void testAddExecutedTask() throws Exception {
        System.out.println("addExecutedTask");
        fail("cos");
        Task task = new Task("Test","Testing",1,1,true,true);
        long startTime = 0L;
        CoreDB instance = new CoreDB();
        boolean expResult = true;
        boolean result = instance.addExecutedTask(task, startTime);
        assertEquals(expResult, result);
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getTodo method, of class CoreDB.
     */
    @Test
    public void testGetTodo() throws Exception {
        System.out.println("getTodo");
        CoreDB instance = new CoreDB();
        ArrayList<Task> expResult = new ArrayList<Task>(2);
        boolean adding = expResult.add(new Task("Test","Poprawiony",1,1,true,true));
        adding = expResult.add(new Task("","Poprawiony",1,1,true,true));
        adding = instance.addTask(new Task("Test","Poprawiony",1,1,true,true));
        adding = instance.addTask(new Task("","Poprawiony",1,1,true,true));
        ArrayList<Task> result = instance.getTodo();
        adding = instance.removeTaskByName("");
        adding = instance.removeTaskByName("Test");
        for (int i=0;i<2;i++){ 
            assertEquals(expResult.get(i).toString(), result.get(i).toString());
        }
        
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
        
    }

    /**
     * Test of getFavourites method, of class CoreDB.
     */
    @Test
    public void testGetFavourites() throws Exception {
        System.out.println("getFavourites");
        CoreDB instance = new CoreDB();
        ArrayList<Task> expResult = new ArrayList<Task>(2);
        boolean adding = expResult.add(new Task("Test","Poprawiony",1,1,true,true));
        adding = expResult.add(new Task("","Poprawiony",1,1,true,true));
        adding = instance.addTask(new Task("Test","Poprawiony",1,1,true,true));
        adding = instance.addTask(new Task("","Poprawiony",1,1,true,true));
        ArrayList<Task> result = instance.getFavourites();
        adding = instance.removeTaskByName("");
        adding = instance.removeTaskByName("Test");
        for (int i=0;i<2;i++){ 
            assertEquals(expResult.get(i).toString(), result.get(i).toString());
        }
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getAllTasks method, of class CoreDB.
     */
    @Test
    public void testGetAllTasks() throws Exception {
        System.out.println("getAllTasks");
        CoreDB instance = new CoreDB();
        ArrayList<Task> expResult = new ArrayList<Task>(2);
        boolean adding = expResult.add(new Task("Test","Poprawiony",1,1,true,true));
        adding = expResult.add(new Task("","Poprawiony",1,1,true,true));
        adding = instance.addTask(new Task("Test","Poprawiony",1,1,true,true));
        adding = instance.addTask(new Task("","Poprawiony",1,1,true,true));
        ArrayList<Task> result = instance.getAllTasks();
        adding = instance.removeTaskByName("");
        adding = instance.removeTaskByName("Test");
        for (int i=0;i<2;i++){ 
            assertEquals(expResult.get(i).toString(), result.get(i).toString());
        }
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getHistory method, of class CoreDB.
     */
    @Test
    public void testGetHistory() throws Exception {
        System.out.println("getHistory");
        CoreDB instance = new CoreDB();
        ArrayList<Task> expResult =new ArrayList<Task>(0);
        ArrayList<Task> result = instance.getHistory();
        assertEquals(expResult, result);
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of getTaskByName method, of class CoreDB.
     */
    @Test
    public void testGetTaskByName() throws Exception {
        System.out.println("getTaskByName");
        String name = "Test";
        CoreDB instance = new CoreDB();
        Task expResult = new Task("Test","Poprawiony",1,1,true,true);
        boolean adding = instance.addTask(expResult);
        Task result = instance.getTaskByName(name);
        assertEquals(expResult.toString(), result.toString());
        adding = instance.removeTaskByName(name);
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getExecutedTask method, of class CoreDB.
     */
    @Test
    public void testGetExecutedTask() throws Exception {
        System.out.println("getExecutedTask");
        fail("");
        String name = "Test";
        CoreDB instance = new CoreDB();
        ExecutedTask expResult = null;
        ExecutedTask result = instance.getExecutedTask(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTaskWithName method, of class CoreDB.
     */
    @Test
    public void testIsTaskWithName() throws Exception {
        System.out.println("isTaskWithName");
        String taskName = "Dupa";
        CoreDB instance = new CoreDB();
        boolean expResult = true;
        boolean adding = instance.addTask(new Task(taskName,"",1,1,true,true));
        boolean result = instance.isTaskWithName(taskName);
        adding = instance.removeTaskByName(taskName);
        assertEquals(expResult, result);
        result = instance.isTaskWithName(taskName);
        assertEquals(false, result);
        System.out.println(" OK: Function passed all tests");
        
    }

    /**
     * Test of getAllExecutedTasks method, of class CoreDB.
     */
    @Test
    public void testGetAllExecutedTasks() throws Exception {
        System.out.println("getAllExecutedTasks");
        CoreDB instance = new CoreDB();
        ArrayList<ExecutedTask> expResult = null;
        ArrayList<ExecutedTask> result = instance.getAllExecutedTasks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class CoreDB.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        CoreDB instance = new CoreDB();
        instance.closeConnection();
        System.out.println(" OK: Function passed all tests");
        
    }

    /**
     * Test of main method, of class CoreDB.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CoreDB.main(args);
        System.out.println(" OK: Function passed all tests");
    }
    
}
