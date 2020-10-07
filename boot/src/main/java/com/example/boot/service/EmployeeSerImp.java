package com.example.boot.service;

import com.example.boot.dao.EmployeeRepo;
import com.example.boot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSerImp implements EmployeeSer {
    @Autowired
    private EmployeeRepo  employeeRepo;

    @Override
    public void deleteEmployeeByid(long id) {
        this.employeeRepo.deleteById(id);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> optional=employeeRepo.findById(id);
        Employee employee=null;
        if(optional.isPresent()){
            employee=optional.get();
        }else{
            throw new RuntimeException("Employee not found for id ::"+ id);
        }
        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.employeeRepo.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }
}
