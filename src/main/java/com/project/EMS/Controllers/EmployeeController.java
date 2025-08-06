package com.project.EMS.Controllers;

import com.project.EMS.Exceptions.EmployeeNotExistException;
import com.project.EMS.Models.Employee;
import com.project.EMS.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.OK).body(savedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws EmployeeNotExistException {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) throws EmployeeNotExistException {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) throws EmployeeNotExistException {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
