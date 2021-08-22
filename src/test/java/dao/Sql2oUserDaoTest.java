package dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {

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
    public void addingUserSetsUserId() throws Exception {
        User testUser = setUpUser();
        int originalUserId=testUser.getUserId();
        userDao.add(testUser);
        assertNotEquals(originalUserId,userDao.getAll().size());
    }

    @Test
    public void getAll() throws Exception {
        User testUser = setUpUser();
        userDao.add(testUser);
        assertEquals(1,userDao.getAll().size());
    }

    @Test
    public void deleteById() throws Exception {
        User testUser = setUpUser();
        userDao.add(testUser);
        userDao.deleteById(testUser.getUserId());
        assertEquals(0,userDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        User testUser = setUpUser();
        User otherUser = setUpUser();
        assertEquals(0,userDao.getAll().size());
    }

    @Test
    public void addUserToDepartment() throws Exception {
        Department testDepartment = setUpDepartment();
        Department OtherDepartment = setUpDepartment();

        departmentDao.add(testDepartment);
        departmentDao.add(OtherDepartment);

        User testUser = setUpUser();

        userDao.add(testUser);

        userDao.addUserToDepartment(testUser,testDepartment);
        userDao.addUserToDepartment(testUser,OtherDepartment);

        assertEquals(2,userDao.getAllDepartmentsForAUser(testUser.getUserId()).size());
    }

    @Test
    public void deletingDepartmentAlsoUpdatesJoinTable() {
        User testUser = setUpUser();
        userDao.add(testUser);

        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);

        departmentDao.addDepartmentToUser(testDepartment,testUser);

        departmentDao.deleteById(testDepartment.getDepartmentId());
        assertEquals(0,departmentDao.getAllUsersForADepartment(testDepartment.getDepartmentId()).size());
    }

    //helper
    public User setUpUser(){
        return  new User("issah","ceo",1);
    }
    public Department setUpDepartment() {
        Department department =  new Department("logistics","most of company books management");
        departmentDao.add(department);
        return department;
    }
}