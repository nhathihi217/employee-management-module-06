package com.example.employeemanagement.employee;

import com.example.employeemanagement.department.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeMvcController {
    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    public EmployeeMvcController(EmployeeService employeeService, DepartmentRepository departmentRepository) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("name", "");
        model.addAttribute("department", "");
        return "employees/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employees/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("employeeForm") EmployeeForm form,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "employees/add";
        }
        employeeService.create(form);
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String department,
                         Model model) {
        model.addAttribute("employees", employeeService.search(name, department));
        model.addAttribute("name", name == null ? "" : name);
        model.addAttribute("department", department == null ? "" : department);
        return "employees/list";
    }
}
