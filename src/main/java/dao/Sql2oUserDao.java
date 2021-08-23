package dao;

import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUserDao implements UserDao{
    private final Sql2o sql2o;

    public Sql2oUserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (userName, companyPosition, departmentId) VALUES (:userName, :companyPosition,:departmentId)";

        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setUserId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addUserToDepartment(User user, Department department) {
        String sql = "INSERT INTO departments_users (userId, departmentId ) VALUES (:userId, :departmentId )";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", user.getUserId())
                    .addParameter("departmentId ", department.getDepartmentId ())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public List<Department> getAllDepartmentsForAUser(int userId) {
        ArrayList<Department> departments = new ArrayList();


        String joinQuery = "SELECT departmentId FROM departments_users WHERE userId = :userId";

        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentIds = con.createQuery(joinQuery)
                    .addParameter("userId", userId)
                    .executeAndFetch(Integer.class); //what is happening in the lines above?
            for (Integer  departmentId : allDepartmentIds ){
                String departmentQuery = "SELECT * FROM departments WHERE id = :departmentId";
                departments.add(
                        con.createQuery(departmentQuery)
                                .addParameter("departmentId", departmentId)
                                .executeAndFetchFirst(Department.class));
            } //why are we doing a second sql query - set?
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public void deleteById(int userId) {
        String sql = "DELETE from users WHERE userId=:userId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from users";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    public User findById(int userId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM uses WHERE userId = :userId")
                    .addParameter("userId", userId)
                    .executeAndFetchFirst(User.class);
        }
    }
}
