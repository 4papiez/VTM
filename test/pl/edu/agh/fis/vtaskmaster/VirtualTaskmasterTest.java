/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster;

import java.util.ArrayList;
import javax.swing.JTable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

/**
 *
 * @author Mateusz Papież
 */
public class VirtualTaskmasterTest {
    
    public VirtualTaskmasterTest() {
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
     * Test of main method, of class VirtualTaskmaster.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        VirtualTaskmaster.main(args);
        System.out.println("PASSED");
    }

    /**
     * Test of handleVTCW method, of class VirtualTaskmaster.
     */
    @Test
    public void testHandleVTCW() {
        System.out.println("handleVTCW");
        int h = 0;
        int min = 0;
        String task = "";
        int prior = 1;
        VirtualTaskmaster instance = new VirtualTaskmaster();
        instance.handleVTCW(h, min, task, prior);
        System.out.println("PASSED");
    }

    /**
     * Test of countDownTime method, of class VirtualTaskmaster.
     */
    @Test
    public void testCountDownTime() {
        System.out.println("countDownTime");
        fail("This is prototype!");
        int winIndx = 0;
        boolean retHourTxt = false;
        VirtualTaskmaster instance = new VirtualTaskmaster();
        String expResult = "0-224409787";
        String result = instance.countDownTime(winIndx, retHourTxt);
        assertEquals(expResult, result);
        System.out.println("PASSED");
    }

    /**
     * Test of elapsedTimeCalc method, of class VirtualTaskmaster.
     */
    @Test
    public void testElapsedTimeCalc() {
        System.out.println("elapsedTimeCalc");
        String hours = "3";
        String minutes = "5";
        VirtualTaskmaster instance = new VirtualTaskmaster();
        int expResult = 11100000;
        int result = instance.elapsedTimeCalc(hours, minutes);
        assertEquals(expResult, result);
        System.out.println("PASSED");
    }

    /**
     * Test of validateDataVTM method, of class VirtualTaskmaster.
     */
    @Test
    public void testValidateDataVTM() {
    	/*
        System.out.println("validateDataVTM");
        int h = 0;
        int min = 0;
        String name = "";
        String desc = "";
        VirtualTaskmaster instance = new VirtualTaskmaster();
        boolean expResult = false;
        boolean result = instance.validateDataVTM(h, min, name, desc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of fillTable method, of class VirtualTaskmaster.
     */
    @Test
    public void testFillTable() {
    	/*
        System.out.println("fillTable");
        JTable tbl = null;
        ArrayList<Task> task = null;
        VirtualTaskmaster instance = new VirtualTaskmaster();
        instance.fillTable(tbl, task);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }
    
}
