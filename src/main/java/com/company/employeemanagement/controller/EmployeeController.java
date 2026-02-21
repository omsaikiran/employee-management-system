package com.company.employeemanagement.controller;

import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;



@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private static final Logger log =
            LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Employee create(@Valid @RequestBody Employee e) {
        log.info("API → Create employee: {}", e);
        return service.save(e);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/all")
    public Page<Employee> getEmployees(@RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @RequestParam("sortBy") String sortBy) {
        log.info("API → Get employees page={} size={} sort={}", page, size, sortBy);
        return service.getAllEmployees(page, size, sortBy);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/department/{departmentId}")
    public Page<Employee> getByDepartment(@PathVariable(name = "departmentId") Long departmentId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        //log.info("Department ID:" + departmentId);
        log.info("API → Get employees by department {}", departmentId);
        Pageable pageable = PageRequest.of(page, size);

        System.out.println("Department ID:" + departmentId);
        return service.getByDepartment(departmentId, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
