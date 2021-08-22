package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getDepartmentName_departmentName() throws Exception {
        Department testDepartment= setUpDepartment();
        assertEquals("logistics",testDepartment.getDepartmentName());
    }

    @Test
    public void setDepartmentName_departmentName() throws Exception  {
        Department testDepartment= setUpDepartment();
        testDepartment.setDepartmentName("finance");
        assertNotEquals("logistics",testDepartment.getDepartmentName());
    }

    @Test
    public void getDescription_description() throws Exception {
        Department testDepartment= setUpDepartment();
        assertEquals("most of company books management",testDepartment.getDescription());
    }

    @Test
    public void setDescription_description() throws Exception {
        Department testDepartment= setUpDepartment();
        testDepartment.setDescription("purchasing and procurement ");
        assertNotEquals("most of company books management",testDepartment.getDescription());
    }

    @Test
    public void testEquals_true() throws Exception {
        Department testDepartment= setUpDepartment();
        Department otherDepartment= setUpDepartment();
        assertTrue(testDepartment.equals(otherDepartment));
    }

    //helpers

    public Department setUpDepartment(){
        return new Department("logistics","most of company books management");
    }
}