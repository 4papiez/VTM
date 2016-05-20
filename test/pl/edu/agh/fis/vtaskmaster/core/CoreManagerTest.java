/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster.core;

import java.util.ArrayList;
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
 * @author Admin2
 */
public class CoreManagerTest {
    
    public CoreManagerTest() {
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
     * Test of finalize method, of class CoreManager.
     */
    @Test
    public void testFinalize() {
        System.out.println("finalize");
        CoreManager instance = new CoreManager();
        instance.finalize();
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of getAllTasks method, of class CoreManager.
     */
    @Test
    public void testGetAllTasks() {
        System.out.println("getAllTasks");
        CoreManager instance = new CoreManager();
        ArrayList<Task> expResult = null;
        ArrayList<Task> result = instance.getAllTasks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllExecutedTasks method, of class CoreManager.
     */
    @Test
    public void testGetAllExecutedTasks() {
        System.out.println("getAllExecutedTasks");
        CoreManager instance = new CoreManager();
        ArrayList<ExecutedTask> expResult = new ArrayList(1);
        expResult.add(new ExecutedTask(1,"",0,123456,1234567,true));
        ArrayList<ExecutedTask> result = instance.getAllExecutedTasks();
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
        instance.removeTaskByName("");
        System.out.println(" OK: Function passed all tests");
        
    }

    /**
     * Test of getExecutedTask method, of class CoreManager.
     */
    @Test
    public void testGetExecutedTask() {
        /*
    	System.out.println("getExecutedTask");
        String name = "";
        CoreManager instance = new CoreManager();
        ExecutedTask expResult = null;
        ExecutedTask result = instance.getExecutedTask(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of getTaskByName method, of class CoreManager.
     */
    @Test
    public void testGetTaskByName() {
        System.out.println("getTaskByName");
        String taskName = "";
        CoreManager instance = new CoreManager();
        Task expResult = null;
        Task result = instance.getTaskByName(taskName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFavourites method, of class CoreManager.
     */
    @Test
    public void testGetFavourites() {
        System.out.println("getFavourites");
        CoreManager instance = new CoreManager();
        ArrayList<Task> expResult = null;
        ArrayList<Task> result = instance.getFavourites();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTodo method, of class CoreManager.
     */
    @Test
    public void testGetTodo() {
        System.out.println("getTodo");
        CoreManager instance = new CoreManager();
        instance.saveTask(new Task("","",1,1,true,true));
        ArrayList<Task> expResult = new ArrayList(1);
        expResult.add(new Task("","",1,1,true,true));
        ArrayList<Task> result = instance.getTodo();
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
        instance.removeTaskByName("");
        System.out.println(" OK: Function passed all tests");
        
    }

    /**
     * Test of getHistory method, of class CoreManager.
     */
    @Test
    public void testGetHistory() {
        System.out.println("getHistory");
        CoreManager instance = new CoreManager();
        ArrayList<Task> expResult = null;
        ArrayList<Task> result = instance.getHistory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeTaskByName method, of class CoreManager.
     */
    @Test
    public void testRemoveTaskByName() {
        System.out.println("removeTaskByName");
        String taskName = "";
        CoreManager instance = new CoreManager();
        boolean expResult = false;
        boolean result = instance.removeTaskByName(taskName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveTask method, of class CoreManager.
     */
    @Test
    public void testSaveTask() {
        System.out.println("saveTask");
        Task task = new Task("","",1,1,true,true);
        CoreManager instance = new CoreManager();
        boolean expResult = true;
        boolean result = instance.saveTask(task);
        assertEquals(expResult, result);
        instance.removeTaskByName("");
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of executeTask method, of class CoreManager.
     */
    @Test
    public void testExecuteTask() {
        System.out.println("executeTask");
        Task task = null;
        long startTime = 0L;
        CoreManager instance = new CoreManager();
        boolean expResult = false;
        boolean result = instance.executeTask(task, startTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateExecutedTask method, of class CoreManager.
     */
    @Test
    public void testUpdateExecutedTask() {
        System.out.println("updateExecutedTask");
        ExecutedTask task = null;
        CoreManager instance = new CoreManager();
        boolean expResult = false;
        boolean result = instance.updateExecutedTask(task);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finishTask method, of class CoreManager.
     */
    @Test
    public void testFinishTask() {
        System.out.println("finishTask");
        ExecutedTask task = null;
        long endTime = 0L;
        CoreManager instance = new CoreManager();
        boolean expResult = false;
        boolean result = instance.finishTask(task, endTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class CoreManager.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CoreManager.main(args);
        
    }
    
}
