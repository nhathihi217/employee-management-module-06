package com.example.employeemanagement.employee;

import com.example.employeemanagement.department.Department;
import com.example.employeemanagement.department.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> findAll() { return employeeRepository.findAll(); }

    public List<Employee> search(String name, String department) {
        if (name != null && !name.isBlank()) {
            return employeeRepository.findByNameContainingIgnoreCase(name.trim());
        }
        if (department != null && !department.isBlank()) {
            return employeeRepository.findByDepartmentNameContainingIgnoreCase(department.trim());
        }
        return employeeRepository.findAll();
    }

    public Employee create(EmployeeForm form) {
        Department department = departmentRepository.findById(form.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + form.getDepartmentId()));
        return employeeRepository.save(new Employee(form.getName().trim(), form.getEmail().trim(), department));
    }
}
