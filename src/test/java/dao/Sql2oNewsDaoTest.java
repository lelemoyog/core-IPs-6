package dao;

import models.Department;
import models.News;
import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {

    private static Connection conn;
    private static Sql2oNewsDao newsDao;
    private static Sql2oDepartmentDao departmentDao;
    private  static Sql2oUserDao userDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organisational_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "issah", "issah9960");
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
    public void addingNewsSetsId() throws Exception {
        News testNews = setUpNews();
        assertEquals(38,testNews.getNewsId());
    }

    @Test
    public void getAll() throws Exception {
        News testNews = setUpNews();
        News otherNews= setUpNews();
        assertEquals(2,newsDao.getAll().size());
    }

    @Test
    public void getAllNewsByDepartment() throws Exception {
        Department testDepartment = setUpDepartment();
        Department otherDepartment = setUpDepartment();
        News news1 = setUpAltNewsForDepartment(testDepartment);
        News news2 = setUpAltNewsForDepartment(testDepartment);
        News newsForOtherDepartment = setUpAltNewsForDepartment(otherDepartment);
        assertEquals(2,newsDao.getAllNewsByDepartment(testDepartment.getDepartmentId()).size());
    }

    @Test
    public void deleteById() throws Exception {
        News testNews = setUpNews();
        News otherNews = setUpNews();
        assertEquals(2,newsDao.getAll().size());
        newsDao.deleteById(testNews.getNewsId());
        assertEquals(1,newsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        News testNews = setUpNews();
        News otherNews= setUpNews();
        newsDao.clearAll();
        assertEquals(0,newsDao.getAll().size());
    }


//helpers
    public News setUpNews() {
        News news =new News("salary rise", 1);
        newsDao.add(news);
        return  news;
    }
    public News setUpAltNewsForDepartment(Department department){
        News news =new  News("salary rise", 1,department.getDepartmentId());
        newsDao.add(news);
        return  news;
    }

    public Department setUpDepartment() {
        Department department =  new Department("logistics","most of company books management");
        departmentDao.add(department);
        return department;
    }
}