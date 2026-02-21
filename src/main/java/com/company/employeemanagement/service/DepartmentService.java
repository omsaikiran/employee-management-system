package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Department;
import com.company.employeemanagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private static final Logger log =
            LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository repo;

    public Department save(Department d) {

        log.info("Creating department: {}", d.getName());

        Department saved = repo.save(d);

        log.info("Department created successfully with ID: {}", saved.getId());

        return saved;
    }

    public List<Department> getAll() {

        log.info("Fetching all departments");

        List<Department> list = repo.findAll();

        log.info("Fetched {} departments", list.size());

        return list;
    }

    public void delete(Long id) {

        log.warn("Deleting department with ID: {}", id);

        repo.deleteById(id);

        log.info("Department deleted successfully: {}", id);
    }
}
