package dao;

import models.Department;
import models.User;

import java.util.List;

public interface DepartmentDao {
    //create
    void add (Department department);
    void addDepartmentToUser(Department department, User user);
    //read
    List<Department> getAll();
    Department findById(int departmentId);
    List<User> getAllUsersForADepartment(int departmentId);
    //update
    void update(int departmentId,String departmentName, String description);
    //delete
    void deleteById(int departmentId);
    void clearAll();
}
