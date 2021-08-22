package models;

import java.util.Objects;

public class News {
    private int newsId;
    private String generalNews;
    private int departmentId;
    private int userId;

    public News(String generalNews, int userId) {
        this.userId = userId;
        this.newsId = newsId;
        this.generalNews = generalNews;
    }

    public News( String generalNews, int userId , int departmentId) {
        this.newsId = newsId;
        this.generalNews = generalNews;
        this.departmentId = departmentId;
        this.userId = userId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getGeneralNews() {
        return generalNews;
    }

    public void setGeneralNews(String generalNews) {
        this.generalNews = generalNews;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return getNewsId() == news.getNewsId() &&
                getDepartmentId() == news.getDepartmentId() &&
                getUserId() == news.getUserId() &&
                getGeneralNews().equals(news.getGeneralNews());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNewsId(), getGeneralNews(), getDepartmentId(), getUserId());
    }


}
