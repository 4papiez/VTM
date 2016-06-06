/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster.core;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

/**
 *
 * @author Mateusz Papież
 */
public class CoreManagerTest {
    /** Instance which has to be initialized before each test case */
    private CoreManager instance;
    /** Name of the testing instance */
    private final static String NAME="TEST";

    @Before
    public void setUp() {
        instance = new CoreManager("test.db");
    }
    
    @After
    public void tearDown() {
        instance.cleardb();
        instance.finalize();
    }

    /**
     * Test of finalize method, of class CoreManager.
     */
    @Test
    public void testFinalize() {
        System.out.println("Finalize()");
        instance = new CoreManager(NAME);
        instance.finalize();
        System.out.println(" PASSED");
    }

    /**
     * Test of getAllTasks method, of class CoreManager.
     */
    @Test
    public void testGetAllTasks() {
        System.out.println("GetAllTasks()");
        Task task = new Task("Test","",1,1,true,true);
        ArrayList<Task> expResult= new ArrayList();
        expResult.add(task);
        instance.saveTask(task);
        ArrayList<Task> result = instance.getAllTasks();
        assertEquals(expResult.toString(), result.toString());
        System.out.println(" PASSED");
    }

    /**
     * Test of getAllExecutedTasks method, of class CoreManager.
     */
    @Test
    public void testGetAllExecutedTasks() {
        System.out.println("getAllExecutedTasks");
        ArrayList<ExecutedTask> expResult = new ArrayList(1);
        expResult.add(new ExecutedTask(1,"Test",0,0,0,false));
        Task task = new Task("Test","",1,1,true,true);
        instance.saveTask(task);
        instance.executeTask(task, 0);
        ArrayList<ExecutedTask> result = instance.getAllExecutedTasks();
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
        System.out.println(" PASSED ");
        
    }

    /**
     * Test of getTaskByName method, of class CoreManager.
     */
    @Test
    public void testGetTaskByName() {
        System.out.println("getTaskByName");
        String taskName = "Test";
        Task expResult = new Task(taskName,"",1,1,true,true);
        instance.saveTask(expResult);
        Task result = instance.getTaskByName(taskName);
        assertEquals(expResult.toString(), result.toString());
        System.out.println(" PASSED");
    }

    /**
     * Test of getFavourites method, of class CoreManager.
     */
    @Test
    public void testGetFavourites() {
        System.out.println("getFavourites");
        ArrayList<Task> expResult = new ArrayList();
        Task one = new Task("Test1","",1,1,true,true);
        Task two = new Task("Test2","",0,0,false,false);
        expResult.add(one);
        instance.saveTask(one);
        instance.saveTask(two);
        ArrayList<Task> result = instance.getFavourites();
        assertEquals(expResult.toString(), result.toString());
        System.out.println("PASSED");
    }

    /**
     * Test of getTodo method, of class CoreManager.
     */
    @Test
    public void testGetTodo() {
        System.out.println("getTodo");
        Task one = new Task("Test1","",1,1,true,true);
        Task two = new Task("Test2","",0,0,false,false);
        instance.saveTask(one);
        instance.saveTask(two);
        ArrayList<Task> expResult = new ArrayList(1);
        expResult.add(one);
        ArrayList<Task> result = instance.getTodo();
        assertEquals(expResult.toString(), result.toString());
        System.out.println(" PASSED");
        
    }

    /**
     * Test of getHistory method, of class CoreManager.
     */
    @Test
    public void testGetHistory() {
        System.out.println("getHistory");
        Task one = new Task("Test1","",1,1,true,true);
        Task two = new Task ("Test 2","",0,0,false,false);
        ExecutedTask eone = new ExecutedTask(1,"Test1" ,0,0,0,true);
        ExecutedTask etwo = new ExecutedTask(2,"Test 2" ,0,0,0,true);
        ArrayList<Task> expResult = new ArrayList();
        expResult.add(one);
        expResult.add(two);
        instance.saveTask(two);
        instance.saveTask(one);
        instance.executeTask(one, 0);
        instance.executeTask(two, 0);
        instance.finishTask(etwo, 0);
        instance.finishTask(eone, 0);
        ArrayList<Task> result = instance.getHistory();
        assertEquals(expResult.toString(), result.toString());
        System.out.println(" PASSED");
    }

    /**
     * Test of removeTaskByName method, of class CoreManager.
     */
    @Test
    public void testRemoveTaskByName() {
        System.out.println("removeTaskByName");
        String taskName = "Test";
        instance.saveTask(new Task(taskName,"",1,1,true,true));
        boolean expResult = true;
        boolean result = instance.removeTaskByName(taskName);
        assertEquals(expResult, result);
        System.out.println(" PASSED");
    }

    /**
     * Test of saveTask method, of class CoreManager.
     */
    @Test
    public void testSaveTask() {
        System.out.println("saveTask");
        Task task = new Task("Test","",1,1,true,true);
        boolean expResult = true;
        boolean result = instance.saveTask(task);
        assertEquals(expResult, result);
        System.out.println(" PASSED");
    }

    /**
     * Test of executeTask method, of class CoreManager.
     */
    @Test
    public void testExecuteTask() {
        System.out.println("executeTask");
        Task task = new Task("Test","",1,1,true,true);
        long startTime = 0L;
        boolean expResult = true;
        instance.saveTask(task);
        boolean result = instance.executeTask(task, startTime);
        assertEquals(expResult, result);
        System.out.println(" PASSED ");
    }

    /**
     * Test of updateExecutedTask method, of class CoreManager.
     */
    @Test
    public void testUpdateExecutedTask() {
        System.out.println("updateExecutedTask");
        Task task = new Task ("Test", "" ,1,1,true,true);
        ExecutedTask etask = new ExecutedTask (1,"Test",0,0,0,false);
        boolean expResult = true;
        instance.saveTask(task);
        instance.executeTask(task, 0);
        boolean result = instance.updateExecutedTask(etask);
        assertEquals(expResult, result);
        System.out.println(" PASSED");
    
    }

    /**
     * Test of finishTask method, of class CoreManager.
     */
    @Test
    public void testFinishTask() {
        System.out.println("finishTask");
        ExecutedTask etask = new ExecutedTask(1,"Test",0,0,0,false);
        Task task = new Task("Test","",1,1,true,true);
        long endTime = 0L;
        instance.saveTask(task);
        instance.executeTask(task, 0);
        boolean expResult = true;
        boolean result = instance.finishTask(etask, endTime);
        assertEquals(expResult, result);
        ArrayList<ExecutedTask> real = instance.getAllExecutedTasks();
        ArrayList<ExecutedTask> expected = new ArrayList();
        expected.add(new ExecutedTask(1,"Test",0,0,0,true));
        System.out.println(expected);
        System.out.println(real);
        assertEquals(expected.toString(), real.toString());
        System.out.println(" PASSED");
    }

    /**
     * Test of main method, of class CoreManager.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CoreManager.main(args);
        System.out.println(" PASSED");
        
    }

    /**
     * Test of cleardb method, of class CoreManager.
     */
    @Test
    public void testCleardb() {
        System.out.println("cleardb");
        instance = new CoreManager(NAME);
        boolean expResult = true;
        boolean result = instance.cleardb();
        assertEquals(expResult, result);
        System.out.println(" PASSED");
    }

    
}
