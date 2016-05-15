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
public class TaskTest {
    
    public TaskTest() {
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
     * Test of getOldName method, of class Task.
     */
    @Test
    public void testGetOldName() {
        System.out.println("getOldName");
        Task instance = new Task("Test","", 1, 123456,true,true);
        String expResult = "Test";
        String result = instance.getOldName();
        assertEquals(expResult, result);
        instance = new Task("","", 1, 123456,true,true);
        expResult = "";
        result = instance.getOldName();
        assertEquals(expResult, result);
        instance = new Task(null,"", 1, 123456,true,true);
        expResult = null;
        result = instance.getOldName();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getExpectedTime method, of class Task.
     */
    @Test
    public void testGetExpectedTime() {
        System.out.println("getExpectedTime");
        Task instance = new Task("","", 1, 123456,true,true);
        long expResult = 123456;
        long result = instance.getExpectedTime();
        assertEquals(expResult, result);
        instance = new Task("","", 1, 0,true,true);
        expResult = 0;
        result = instance.getExpectedTime();
        assertEquals(expResult, result);
        instance = new Task("","", 1, -32175,true,true);
        expResult = -32175;
        result = instance.getExpectedTime();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of setExpectedTime method, of class Task.
     */
    @Test
    public void testSetExpectedTime() {
        System.out.println("setExpectedTime");
        long expectedTime = 0L;
        Task instance = new Task("","",1,100,true,true);
        instance.setExpectedTime(expectedTime);
        long result = instance.getExpectedTime();
        assertEquals(expectedTime, result);
        System.out.println(" OK: Function passed all tests");  
    }

    /**
     * Test of getName method, of class Task.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Task instance = new Task("Test","", 1, 123456,true,true);
        String expResult = "Test";
        String result = instance.getName();
        assertEquals(expResult, result);
        instance = new Task("","", 1, 123456,true,true);
        expResult = "";
        result = instance.getName();
        assertEquals(expResult, result);
        instance = new Task("~!@#$%^&*()_+","", 1, 123456,true,true);
        expResult = "~!@#$%^&*()_+";
        result = instance.getName();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of setName method, of class Task.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Task instance = new Task("","", 1, 123456,true,true);
        instance.setName(name);
        String result = instance.getName();
        assertEquals(name, result);
        name = "~!@#$%^&*()_+";
        instance.setName(name);
        result = instance.getName();
        assertEquals(name, result);
        name = "1234567890";
        instance.setName(name);
        result = instance.getName();
        assertEquals(name, result);
        name = "\t\n\\|\"";
        instance.setName(name);
        result = instance.getName();
        assertEquals(name, result);
        System.out.println(" OK: Function passed all tests");
    
    }

    /**
     * Test of getDescription method, of class Task.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Task instance = new Task("","This is testing task...~!@##$%^&*(",1,1,true,true);
        String expResult = "This is testing task...~!@##$%^&*(";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of setDescription method, of class Task.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "";
        Task instance = new Task("","",1,1,true,true);
        instance.setDescription(description);
        String result = instance.getDescription();
        assertEquals(description, result);
        description = "~!@#$%^&*()_+}{\";\t\t\t\t\t\n\n\n\n\n\n\n Strange text";
        instance.setDescription(description);
        result = instance.getDescription();
        assertEquals(description, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of getPriority method, of class Task.
     */
    @Test
    public void testGetPriority() {
        System.out.println("getPriority");
        Task instance = new Task("","",0,1,true,true);
        int expResult = 0;
        int result = instance.getPriority();
        assertEquals(expResult, result);
        instance = new Task("","",10,1,true,true);
        expResult = 10;
        result = instance.getPriority();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests"); 
    }

    /**
     * Test of setPriority method, of class Task.
     */
    @Test
    public void testSetPriority() {
        System.out.println("setPriority");
        Task instance =new Task("","",0,1,true,true);
        for (int priority = 0;priority<11;priority++)
            instance.setPriority(priority);
        int result = instance.getPriority();
        assertEquals(10, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of isFavourite method, of class Task.
     */
    @Test
    public void testIsFavourite() {
        System.out.println("isFavourite");
        Task instance = new Task("","",1,1,false,true);
        boolean expResult = false;
        boolean result = instance.isFavourite();
        assertEquals(expResult, result);
        instance = new Task("","",1,1,true,true);
        expResult = true;
        result = instance.isFavourite();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of setFavourite method, of class Task.
     */
    @Test
    public void testSetFavourite() {
        System.out.println("setFavourite");
        boolean favourite = false;
        Task instance = new Task("","",1,1,true,true);
        instance.setFavourite(favourite);
        assertEquals(false, instance.isFavourite());
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of isTodo method, of class Task.
     */
    @Test
    public void testIsTodo() {
        System.out.println("isTodo");
        Task instance = new Task("","",1,1,true,false);
        boolean expResult = false;
        boolean result = instance.isTodo();
        assertEquals(expResult, result);
        instance = new Task("","",1,1,true,true);
        expResult = true;
        result = instance.isTodo();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of setTodo method, of class Task.
     */
    @Test
    public void testSetTodo() {
        System.out.println("setTodo");
        boolean todo = false;
        Task instance = new Task("","",1,1,true,true);
        instance.setTodo(todo);
        boolean result = instance.isTodo();
        assertEquals(todo, result);
        System.out.println(" OK: Function passed all tests");
    }

    /**
     * Test of toString method, of class Task.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Task instance = new Task("Test","This is testing task.",1,1,true,true);
        String expResult = "Task{" +
                "name='" + "Test" + '\'' +
                ", description='" + "This is testing task." + '\'' +
                ", priority=" + 1 +
                ", expected time=" + 1 +
                ", favourite=" + true +
                ", todo=" + true +
                '}';
        String result = instance.toString();
        assertEquals(expResult, result);
        System.out.println(" OK: Function passed all tests");
    }
    
}
