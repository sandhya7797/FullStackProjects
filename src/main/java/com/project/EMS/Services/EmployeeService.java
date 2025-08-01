package com.project.EMS.Services;

import com.project.EMS.Exceptions.EmployeeNotExistException;
import com.project.EMS.Models.Employee;
import com.project.EMS.Repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id) throws EmployeeNotExistException {
        Optional<Employee> emp = employeeRepository.findById(id);
        if(emp.isPresent()) {
            return emp.get();
        } else {
            throw new EmployeeNotExistException("Employee not exists with id: " + id);
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) throws EmployeeNotExistException {
        Employee existingEmp = getEmployeeById(id);
        existingEmp.setName(employee.getName());
        existingEmp.setEmail(employee.getEmail());
        existingEmp.setDepartment(employee.getDepartment());
        existingEmp.setContact(employee.getContact());
        return employeeRepository.save(existingEmp);
    }

    public void deleteEmployeeById(Long id) throws EmployeeNotExistException {
        Employee emp = getEmployeeById(id);
        employeeRepository.delete(emp);
    }
}
