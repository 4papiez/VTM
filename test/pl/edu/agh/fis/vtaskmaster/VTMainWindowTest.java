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
public class VTMainWindowTest {
    
    public VTMainWindowTest() {
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
     * Test of timeFiller method, of class VTMainWindow.
     */
    @Test
    public void testTimeFiller() {
        System.out.println("timeFiller");
        int toFill = 0;
        String expResult = "00";
        String result = VTMainWindow.timeFiller(toFill);
        assertEquals(expResult, result);
        toFill = 9;
        expResult = "09";
        result = VTMainWindow.timeFiller(toFill);
        assertEquals(expResult, result);
        toFill = 10;
        expResult = "10";
        result = VTMainWindow.timeFiller(toFill);
        assertEquals(expResult, result);
        toFill = 100;
        expResult = "100";
        result = VTMainWindow.timeFiller(toFill);
        assertEquals(expResult, result);
    }
    
}
