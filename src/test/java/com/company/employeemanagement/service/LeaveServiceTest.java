package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.entity.LeaveRequest;
import com.company.employeemanagement.enums.LeaveStatus;
import com.company.employeemanagement.messaging.LeaveProducer;
import com.company.employeemanagement.repository.EmployeeRepository;
import com.company.employeemanagement.repository.LeaveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.company.employeemanagement.enums.LeaveStatus.APPROVED;
import static com.company.employeemanagement.repository.EmployeeRepository.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveRepository repo;

    @Mock
    private RabbitService rabbitService;

    @Mock
    private LeaveProducer producer;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LeaveService service;

    @Test
    void shouldRequestLeave() {
        Employee emp = new Employee();
        emp.setId(1L);
        LeaveRequest leave = new LeaveRequest();
        //leave.id(1L);
        leave.setEmployee(emp);
        leave.setStatus(LeaveStatus.PENDING);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(repo.save(Mockito.any()))
                .thenReturn(leave);
        //Mockito.doNothing().when(rabbitService).sendLeaveNotification((Mockito.any()));

        LeaveRequest saved = service.requestLeave(leave);

        assertNotNull(saved);
        assertEquals(LeaveStatus.PENDING, saved.getStatus());
    }

    @Test
    void shouldApproveLeave() {

        Employee emp = new Employee();
        emp.setId(1L);
        long s = 1L;
        LeaveRequest leave = new LeaveRequest();
        leave.setId(1L);
        leave.setEmployee(emp);
        leave.setStatus(LeaveStatus.PENDING);

        when(repo.findById(1L))
                .thenReturn(Optional.of(leave));
        when(repo.save(Mockito.any()))
                .thenReturn(leave);

        Mockito.doNothing().when(rabbitService).sendLeaveNotification((Mockito.any()));

        LeaveRequest updated = service.updateStatus(s,"APPROVED");

        assertEquals(LeaveStatus.APPROVED, updated.getStatus());
    }
}
