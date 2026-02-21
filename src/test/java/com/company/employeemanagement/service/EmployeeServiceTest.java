package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Department;
import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.messaging.LeaveProducer;
import com.company.employeemanagement.repository.DepartmentRepository;
import com.company.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repo;

    @Mock
    private DepartmentRepository deptrepo;

    @Mock
    private LeaveProducer producer;


    @InjectMocks
    private EmployeeService service;

    @Test
    void shouldSaveEmployee() {

        Department department = new Department();
        department.setId(1L);

        Employee e = new Employee();
        e.setId(1L);
        e.setDepartment(department);
        Mockito.when(deptrepo.findById(1L)).thenReturn(Optional.of(department));
        Mockito.when(repo.save(Mockito.any(Employee.class)))
                .thenReturn(e);
        Mockito.doNothing().when(producer).send(Mockito.anyString());

        Employee saved = service.save(e);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
    }
}
