package com.example.boot.service;

import com.example.boot.entity.Employee;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface EmployeeSer {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeByid(long id);
}
