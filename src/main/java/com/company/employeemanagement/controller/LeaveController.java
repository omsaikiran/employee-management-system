package com.company.employeemanagement.controller;

import com.company.employeemanagement.entity.LeaveRequest;
import com.company.employeemanagement.enums.LeaveStatus;
import com.company.employeemanagement.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {
    private static final Logger log =
            LoggerFactory.getLogger(LeaveController.class);
    private final LeaveService service;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public LeaveRequest request(@RequestBody LeaveRequest leave) {

        log.info("API → Leave request created by employee {}", leave);

        return service.requestLeave(leave);
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaves() {
        return service.getAllLeaves();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/employee/{id}")
    public List<LeaveRequest> byEmployee(@PathVariable Long id) {

        log.info("API → Fetch leaves for employee {}", id);

        return service.getByEmployee(id);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status/{status}")
    public LeaveRequest updateStatus(@PathVariable Long id, @PathVariable String status) {

        log.warn("API → Updating leave {} status to {}", id, status);

        return service.updateStatus(id, status);
    }



}
