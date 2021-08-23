import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import exceptions.ApiException;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oUserDao userDao;
        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "issah", "issah9960");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();


        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            res.type("application/json");
            return gson.toJson(department);
        });

        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            res.type("application/json");
            return gson.toJson(user);
        });

        post("/departments/:departmentId/news/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            News news = gson.fromJson(req.body(), News.class);

            news.setDepartmentId(departmentId); //we need to set this separately because it comes from our route, not our JSON input.
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        post("/departments/:departmentId/user/:userId", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            int userId = Integer.parseInt(req.params("userId"));
            Department department = departmentDao.findById(departmentId);
            User user = userDao.findById(userId);

            if (department != null && user != null){
                //both exist and can be associated - we should probably not connect things that are not here.
                userDao.addUserToDepartment(user,department);
                res.status(201);
                return gson.toJson(String.format("Department '%s' and User '%s' have been associated",user.getUserName(), department.getDepartmentName()));
            }
            else {
                throw new ApiException(404, String.format("Department or User does not exist"));
            }
        });

        get("/users", "application/json", (req, res) -> {
            return gson.toJson(userDao.getAll());
        });

        get("/departments", "application/json", (req, res) -> { //accept a request in format JSON from an app
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size()>0){
                res.type("application/json");
                return gson.toJson(departmentDao.getAll());
            }else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }
        });



        get("/departments/:departmentId", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));

            Department departmentToFind = departmentDao.findById(departmentId);

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the departmentId: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(departmentToFind);
        });

        get("/departments/:departmentId/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the departmentId: \"%s\" exists", req.params("id")));
            }

            allNews = newsDao.getAllNewsByDepartment(departmentId);

            return gson.toJson(allNews);
        });

        get("/departments/:departmentId/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the departmentId : \"%s\" exists", req.params("id")));
            }

            else {
                return gson.toJson(departmentDao.getAllUsersForADepartment(departmentId));
            }
        });




        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = (ApiException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) ->{
            res.type("application/json");
        });
    }
}
