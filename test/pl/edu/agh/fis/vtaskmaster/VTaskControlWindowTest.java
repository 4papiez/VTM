/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin2
 */
public class VTaskControlWindowTest {
    
    public VTaskControlWindowTest() {
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
     * Test of main method, of class VTaskControlWindow.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        VTaskControlWindow.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTask method, of class VTaskControlWindow.
     */
    @Test
    public void testSetTask() {
        System.out.println("setTask");
        String taskName = "";
        String taskTimeHours = "";
        String taskTimeMins = "";
        VTaskControlWindow instance = null;
        instance.setTask(taskName, taskTimeHours, taskTimeMins);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
