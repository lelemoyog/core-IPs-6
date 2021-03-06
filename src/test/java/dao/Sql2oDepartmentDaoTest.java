package dao;

import models.Department;
import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private static Connection conn;
    private static Sql2oNewsDao newsDao;
    private static Sql2oDepartmentDao departmentDao;
    private  static Sql2oUserDao userDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString ="jdbc:postgresql://ec2-35-153-91-18.compute-1.amazonaws.com:5432/d842dhkkvbdkfb";
        Sql2o sql2o = new Sql2o(connectionString, "mkqdfzxqhcwucx", "2ef14d0da0e121c145b3b7e0f9fc755897b176be311f978473de14a63f23e708");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        newsDao.clearAll();
        departmentDao.clearAll();
        userDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingDepartmentSetsId() {
        Department testDepartment = setUpDepartment();
        assertNotEquals(0,testDepartment.getDepartmentId());
    }

    @Test
    public void getAll() {
        Department testDepartment = setUpDepartment();
        assertEquals(1,departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment() {
        Department testDepartment = setUpDepartment();
        Department otherDepartment = setUpDepartment();
        assertEquals(testDepartment,departmentDao.findById(testDepartment.getDepartmentId()));
    }

    @Test
    public void updatesCorrectly() {
        Department testDepartment = setUpDepartment();
        departmentDao.update(testDepartment.getDepartmentId(),"finance","cost of shipping company goods");
        Department foundDepartment = departmentDao.findById(testDepartment.getDepartmentId());
        assertEquals("cost of shipping company goods",foundDepartment.getDescription());
    }

    @Test
    public void deleteById() {
        Department testDepartment = setUpDepartment();
        Department OtherDepartment = setUpDepartment();
        departmentDao.deleteById(testDepartment.getDepartmentId());
        assertEquals(1,departmentDao.getAll().size());
    }

    @Test
    public void clearAll() {
        Department testDepartment = setUpDepartment();
        Department OtherDepartment = setUpDepartment();
        departmentDao.clearAll();
        assertEquals(0,departmentDao.getAll().size());
    }

    @Test
    public void addDepartmentToUser() {
        User testuser = new User("issah","ceo",1);
        userDao.add(testuser);

        User otherUser = new User("aisha","HR",1);
        userDao.add(otherUser);

        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        departmentDao.addDepartmentToUser(testDepartment,testuser);
        departmentDao.addDepartmentToUser(testDepartment,otherUser);

        User[] users = {testuser,otherUser};

        assertEquals(Arrays.asList(users),departmentDao.getAllUsersForADepartment(testDepartment.getDepartmentId()));
    }

    //helpers
    public Department setUpDepartment() {
        Department department =  new Department("logistics","most of company books management");
        departmentDao.add(department);
        return department;
    }
}