package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Department;
import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.messaging.LeaveProducer;
import com.company.employeemanagement.repository.DepartmentRepository;
import com.company.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
//import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository repo;
    private final DepartmentRepository departmentRepo;
    private final LeaveProducer producer;

    public Employee save(Employee e) {
        log.info("Creating employee with email: {}", e.getEmail());
        Department dept = departmentRepo.findById(e.getDepartment().getId()).
                orElseThrow(() -> new RuntimeException("Department not found"));
        e.setDepartment(dept);
        e.setCreatedAt(LocalDateTime.now());
      //  return repo.save(e);
        Employee saved = repo.save(e);
        log.info("Employee created successfully with ID: {}",
                saved.getId() + " " + saved.getEmpName());
        String message = "New Employee Created: "
                + "ID: " + saved.getId() + "Name:" + saved.getEmpName() + "Email: " + saved.getEmail();
        producer.send(message);
        return saved;
    }

    public Page<Employee> getAllEmployees(int page, int size, String sortBy) {

        log.info("Fetching employees | page={} size={} sort={}", page, size, sortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Employee> result = repo.findAll(pageable);

        log.info("Fetched {} employees", result.getTotalElements());

        return result;
    }


    public Page<Employee> getByDepartment(Long deptId, Pageable pageable) {
        return repo.findByDepartment_Id(deptId, pageable);
    }
    public void delete(Long id) {

        log.warn("Deleting employee with ID: {}", id);

        repo.deleteById(id);

        log.info("Employee deleted successfully: {}", id);
    }

}
