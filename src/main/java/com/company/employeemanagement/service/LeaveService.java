package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.entity.LeaveRequest;
import com.company.employeemanagement.enums.LeaveStatus;
import com.company.employeemanagement.messaging.LeaveProducer;
import com.company.employeemanagement.repository.EmployeeRepository;
import com.company.employeemanagement.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository repo;
    private final EmployeeRepository employeeRepo;
    private final RabbitService rabbitService;
    private static final Logger log = LoggerFactory.getLogger(LeaveService.class);


    public LeaveRequest requestLeave(LeaveRequest leave) {
        Employee employee = employeeRepo.findById(leave.getEmployee().getId()).orElseThrow(()
                -> new RuntimeException("employee not found"));
        leave.setEmployee(employee);
        leave.setStatus(LeaveStatus.PENDING);
        return repo.save(leave);
    }

    public List<LeaveRequest> getByEmployee(Long empId) {
        return repo.findByEmployee_Id(empId);
    }

    public List<LeaveRequest> getAllLeaves() {
        return repo.findAll();
    }

    public LeaveRequest updateStatus(Long id, String status) {
        LeaveRequest leave = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(LeaveStatus.valueOf(status.toUpperCase()));

        LeaveRequest saved = repo.save(leave);

        rabbitService.sendLeaveNotification(saved);
        log.info("Leave {} status updated to {}", leave.getId(), status);
        return saved;
    }

}
