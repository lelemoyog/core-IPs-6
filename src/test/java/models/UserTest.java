package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUserName() throws Exception {
        User testUser = setUpUser();
        assertEquals("issah",testUser.getUserName());
    }

    @Test
    public void setUserName() throws Exception {
        User testUser = setUpUser();
        testUser.setUserName("williamson");
        assertNotEquals("issah",testUser.getUserName());
    }

    @Test
    public void getCompanyPosition() throws Exception {
        User testUser = setUpUser();
        assertEquals("ceo",testUser.getCompanyPosition());
    }

    @Test
    public void setCompanyPosition() throws Exception {
        User testUser = setUpUser();
        testUser.setCompanyPosition("manager");
        assertNotEquals("ceo",testUser.getCompanyPosition());
    }

    @Test
    public void getDepartmentId()throws Exception  {
        User testUser = setUpUser();
        assertEquals(1,testUser.getDepartmentId());
    }

    @Test
    public void testEquals() throws Exception {
        User testUser = setUpUser();
        User otherUser = setUpUser();
        assertTrue(testUser.equals(otherUser));
    }

    // helpers

    public User setUpUser(){
        return  new User("issah","ceo",1);
    }
}