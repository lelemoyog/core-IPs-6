package dao;

import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao {
    private final Sql2o sql2o;

    public Sql2oDepartmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments (departmentName, description) VALUES (:departmentName, :description)";

        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setDepartmentId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addDepartmentToUser(Department department, User user) {
        String sql = "INSERT INTO departments_users ( departmentId ,userId) VALUES ( :departmentId ,:userId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId ", department.getDepartmentId ())
                    .addParameter("userId", user.getUserId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public Department findById(int departmentId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments WHERE departmentId = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public List<User> getAllUsersForADepartment(int departmentId) {
        ArrayList<User> users = new ArrayList();
        String joinQuery = "SELECT userId FROM departments_users WHERE  departmentId= :departmentId";

        try (Connection con = sql2o.open()) {
            List<Integer> allUserIds = con.createQuery(joinQuery)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Integer.class); //what is happening in the lines above?
            for (Integer  userId : allUserIds ){
                String departmentQuery = "SELECT * FROM departments WHERE id = :departmentId";
                users.add(
                        con.createQuery(departmentQuery)
                                .addParameter("userId", userId)
                                .executeAndFetchFirst(User.class));
            } //why are we doing a second sql query - set?
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

        return users;
    }

    @Override
    public void update(int departmentId,String departmentName, String description) {
        String sql = "UPDATE departments SET (departmentId,departmentName, description) = (:departmentId, :departmentName, :description) WHERE departmentId=:departmentId"; //CHECK!!!
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId",departmentId)
                    .addParameter("departmentName", departmentName)
                    .addParameter("description", description)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int departmentId) {
        String sql = "DELETE from departments WHERE departmentId=:departmentId";
        String deleteJoin = "DELETE from departments_users WHERE departmentId = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", departmentId)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("departmentId", departmentId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


}
