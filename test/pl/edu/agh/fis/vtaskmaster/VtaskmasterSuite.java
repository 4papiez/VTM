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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Mateusz Papież
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({pl.edu.agh.fis.vtaskmaster.VirtualTaskmasterTest.class, pl.edu.agh.fis.vtaskmaster.core.CoreSuite.class, pl.edu.agh.fis.vtaskmaster.VTasksManagerTest.class, pl.edu.agh.fis.vtaskmaster.VTaskControlWindowTest.class, pl.edu.agh.fis.vtaskmaster.VTMainWindowTest.class})
public class VtaskmasterSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
