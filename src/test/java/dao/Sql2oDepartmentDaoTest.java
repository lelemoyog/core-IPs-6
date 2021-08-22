package dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private Connection conn;
    private Sql2oNewsDao newsDao;
    private Sql2oDepartmentDao departmentDao;
    private  Sql2oUserDao userDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "issah", "issah9960");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
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