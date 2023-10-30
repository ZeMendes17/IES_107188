package ies.lab3.lab3_2.controllers;

import ies.lab3.lab3_2.entities.Employee;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ies.lab3.lab3_2.repositories.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(@RequestParam(required = false) String email) {
        if (email == null) {
            return employeeRepository.findAll();
        } else {
            return employeeRepository.findByEmail(email);
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok().body(savedEmployee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        employeeRepository.delete(employee);
        return ResponseEntity.ok().body("Employee deleted successfully");
    }
}
