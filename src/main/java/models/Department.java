package models;

import java.util.Objects;

public class Department {
    private int departmentId;
    private String departmentName;
    private String description;
    private int numberOfEmployees;


    public Department(String departmentName, String description) {
        this.departmentName = departmentName;
        this.description = description;
        this.departmentId = departmentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return getDepartmentId() == that.getDepartmentId() &&
                getDepartmentName().equals(that.getDepartmentName()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDepartmentId(), getDepartmentName(), getDescription());
    }


}
