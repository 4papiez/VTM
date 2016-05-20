/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.fis.vtaskmaster;

import javax.swing.JTable;
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
public class VTasksManagerTest {
    
    public VTasksManagerTest() {
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
     * Test of main method, of class VTasksManager.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        VTasksManager.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tblFindEmptyRow method, of class VTasksManager.
     */
    @Test
    public void testTblFindEmptyRow() {
        System.out.println("tblFindEmptyRow");
        JTable tbl = null;
        int expResult = 0;
        int result = VTasksManager.tblFindEmptyRow(tbl);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHour method, of class VTasksManager.
     */
    @Test
    public void testGetHour() {
        System.out.println("getHour");
        String time = "";
        boolean minute = false;
        int expResult = 0;
        int result = VTasksManager.getHour(time, minute);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
