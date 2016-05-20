/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster.core.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mateusz Papież
 */
public class ExecutedTaskTest {
    
    public ExecutedTaskTest() {
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
     * Test of getId method, of class ExecutedTask.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        ExecutedTask instance = new ExecutedTask(0,"", 0,123456,123456,false);
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        instance = new ExecutedTask(1,"", 0,123456,123456,false);
        expResult = 1;
        result = instance.getId();
        assertEquals(expResult, result);
        instance = new ExecutedTask(100,"", 0,123456,123456,false);
        expResult = 100;
        result = instance.getId();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");    
    }

    /**
     * Test of getTaskName method, of class ExecutedTask.
     */
    @Test
    public void testGetTaskName() {
        System.out.println("getTaskName");
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,123456,false);
        String expResult = "TEST";
        String result = instance.getTaskName();
        assertEquals(expResult, result);
        instance = new ExecutedTask(0,"", 0,123456,123456,false);
        expResult = "";
        result = instance.getTaskName();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of getElapsedTime method, of class ExecutedTask.
     */
    @Test
    public void testGetElapsedTime() {
        System.out.println("getElapsedTime");
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,0,false);
        long expResult = 0L;
        long result = instance.getElapsedTime();
        assertEquals(expResult, result);
        instance = new ExecutedTask(0,"TEST", 0,123456,123456,false);
        expResult = 123456L;
        result = instance.getElapsedTime();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of setElapsedTime method, of class ExecutedTask.
     */
    @Test
    public void testSetElapsedTime() {
        System.out.println("setElapsedTime");
        long elapsedTime = 0L;
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,123456,false);
        instance.setElapsedTime(elapsedTime);
        long result = instance.getElapsedTime();
        assertEquals(0L, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of isDone method, of class ExecutedTask.
     */
    @Test
    public void testIsDone() {
        System.out.println("isDone");
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,123456,false);
        boolean expResult = false;
        boolean result = instance.isDone();
        assertEquals(expResult, result);
        instance = new ExecutedTask(0,"TEST", 0,123456,123456,true);
        expResult = true;
        result = instance.isDone();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of setDone method, of class ExecutedTask.
     */
    @Test
    public void testSetDone() {
        System.out.println("setDone");
        boolean done = false;
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,123456,true);
        instance.setDone(done);
        boolean result = instance.isDone();
        assertEquals(done, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getStartTime method, of class ExecutedTask.
     */
    @Test
    public void testGetStartTime() {
        System.out.println("getStartTime");
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,123456,true);
        long expResult = 0L;
        long result = instance.getStartTime();
        assertEquals(expResult, result);
        instance = new ExecutedTask(0,"TEST", 123456,1234567,12345678,true);
        expResult = 123456L;
        result = instance.getStartTime();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getEndTime method, of class ExecutedTask.
     */
    @Test
    public void testGetEndTime() {
        System.out.println("getEndTime");
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,1234567,true);
        long expResult = 123456L;
        long result = instance.getEndTime();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of setEndTime method, of class ExecutedTask.
     */
    @Test
    public void testSetEndTime() {
        System.out.println("setEndTime");
        long endTime = 0L;
        ExecutedTask instance = new ExecutedTask(0,"TEST", 0,123456,1234567,true);
        instance.setEndTime(endTime);
        long result = instance.getEndTime();
        assertEquals(endTime, result);
        System.out.println(" OK: Function passed all tests");
        
    }

    /**
     * Test of toString method, of class ExecutedTask.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ExecutedTask instance = new ExecutedTask(1,"TEST", 0,123456,1234567,true);
        String expResult = "ExecutedTask{" +
                "id = " + 1 +
                ", taskName=" + "TEST" +
                ", startTime=" + 0 +
                ", endTime=" + 123456 +
                ", elapsedTime=" + 1234567 +
                ", done=" + true +
                '}';
        String result = instance.toString();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }
    
}
