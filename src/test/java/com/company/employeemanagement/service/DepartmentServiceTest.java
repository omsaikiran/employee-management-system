package com.company.employeemanagement.service;

import com.company.employeemanagement.entity.Department;
import com.company.employeemanagement.entity.Employee;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository repo;

    @InjectMocks
    private DepartmentService service;

    @Test
    void shouldSaveDepartment() {

        Department d = new Department();
        d.setName("IT");

        Mockito.when(repo.save(Mockito.any()))
                .thenReturn(d);

        Department saved = service.save(d);

        assertNotNull(saved);
        assertEquals("IT", saved.getName());
    }

    @Test
    void shouldGetAllDepartments() {

        List<Department> list = List.of(
                new Department(1L, "IT", "Hyd", null),
                new Department(2L, "HR", "Chennai", null)
        );

        Mockito.when(repo.findAll()).thenReturn(list);

        List<Department> result = service.getAll();

        assertEquals(2, result.size());
    }
}
