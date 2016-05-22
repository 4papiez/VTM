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
 * @author Mateusz Papież
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
        System.out.println("PASSED");
    }

    /**
     * Test of setTask method, of class VTaskControlWindow.
     */
    @Test
    public void testSetTask() {
        System.out.println("setTask");
        String taskName = "Test";
        String taskTimeHours = "1";
        String taskTimeMins = "1";
        VTaskControlWindow instance = new VTaskControlWindow(taskName,taskTimeHours,taskTimeMins);
        instance.setTask(taskName, taskTimeHours, taskTimeMins);
        System.out.println("PASSED");
    }
    
}
