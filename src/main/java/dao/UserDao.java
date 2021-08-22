package dao;

import models.Department;
import models.User;

import java.util.List;

public interface UserDao {
    //create
    void add (User user);
    void addUserToDepartment(User user, Department department);

    //read
    List<User> getAll();
    List<Department> getAllDepartmentsForAUser(int userId);

    //delete
    void  deleteById(int userId);
    void clearAll();
}
