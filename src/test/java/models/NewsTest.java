package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void getGeneralNews_generalNews() throws Exception {
        News testNews = setUpNews();
        assertEquals("salary rise" ,testNews.getGeneralNews());
    }

    @Test
    public void setGeneralNews_generalNews() throws Exception {
        News testNews = setUpNews();
        testNews.setGeneralNews("holy day");
        assertNotEquals("salary rise",testNews.getGeneralNews());
    }

    @Test
    public void getDepartmentId_departmentId() throws Exception {
        News testNews = setUpAltNews();
        assertEquals(2 ,testNews.getDepartmentId());
    }


    @Test
    public void getUserId_userId()  throws Exception{
        News testNews = setUpAltNews();
        assertEquals(1 ,testNews.getUserId());
    }



    @Test
    public void testEquals_true() throws Exception {
        News testNews = setUpAltNews();
        News otherNews = setUpAltNews();
        assertTrue(testNews.equals(otherNews));

    }

    //helpers

    public News setUpNews() {
        return  new News("salary rise",1);
    }

    public News setUpAltNews(){
        return  new News("salary rise",1,2);
    }
}