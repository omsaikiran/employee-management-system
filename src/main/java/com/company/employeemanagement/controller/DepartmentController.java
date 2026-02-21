package com.company.employeemanagement.controller;

import com.company.employeemanagement.entity.Department;
import com.company.employeemanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private static final Logger log =
            LoggerFactory.getLogger(DepartmentController.class);


    private final DepartmentService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Department create(@RequestBody Department d) {
        log.info("API request → create department");
        return service.save(d);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Department> list() {
        log.info("API request → list departments");
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("API request → delete department {}", id);
        service.delete(id);
    }
}