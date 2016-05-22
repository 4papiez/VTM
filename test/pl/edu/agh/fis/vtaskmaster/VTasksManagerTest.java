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
 * @author Mateusz Papież
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
        System.out.println("PASSED");
    }

    /**
     * Test of tblFindEmptyRow method, of class VTasksManager.
     */
    @Test
    public void testTblFindEmptyRow() {
        System.out.println("tblFindEmptyRow");
        VTasksManager vtm = new VTasksManager();
        JTable tbl = vtm.tblFavourites;
        int expResult = 0;
        int result = VTasksManager.tblFindEmptyRow(tbl);
        assertEquals(expResult, result);
        System.out.println("PASSED");
    }

    /**
     * Test of getHour method, of class VTasksManager.
     */
    @Test
    public void testGetHour() {
        System.out.println("getHour");
        String time = "12";
        boolean minute = false;
        int expResult = 0;
        int result = VTasksManager.getHour(time, minute);
        assertEquals(expResult, result);
        time = "1234";
        result = VTasksManager.getHour(time, minute);
        expResult = 1;
        assertEquals(expResult, result);
        time = "1234";
        minute = true;
        result = VTasksManager.getHour(time, minute);
        expResult = 34 ;
        assertEquals(expResult, result);
        time = "12345";
        expResult = 45;
        result = VTasksManager.getHour(time, minute);
        assertEquals(expResult, result);
        time = "12345";
        minute = false;
        expResult = 12;
        result = VTasksManager.getHour(time, minute);
        assertEquals(expResult, result);
        System.out.println("PASSED");
    }
    
}
