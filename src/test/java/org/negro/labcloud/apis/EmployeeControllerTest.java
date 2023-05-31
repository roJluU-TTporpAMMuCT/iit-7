package org.negro.labcloud.apis;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.negro.labcloud.controllers.EmployeeController;
import org.negro.labcloud.models.Employee;
import org.negro.labcloud.repos.EmployeeRepo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @MockBean
    private EmployeeRepo erep;

    @InjectMocks
    private EmployeeController capi;

    private Employee emp = Employee.builder()
            .id(1L)
            .firstname("John")
            .lastname("Doe")
            .phone("2248992745")
            .position("driver")
            .build();

    @Test
    @Order(1)
    public void saveEmployeeTest(){
        when(erep.save(any(Employee.class))).thenReturn(emp);
        Assertions.assertEquals(emp, capi.createEmployee(emp));
    }

    @Test
    @Order(2)
    public void getEmployeeTest(){
        when(erep.findById(any(Long.class))).thenReturn(Optional.ofNullable(emp));
        Assertions.assertEquals(ResponseEntity.ok(emp), capi.getEmployee(1L));
    }

    @Test
    @Order(3)
    public void updateEmployeeTest(){
        when(erep.findById(any(Long.class))).thenReturn(Optional.ofNullable(emp));
        when(erep.save(any(Employee.class))).thenReturn(emp);
        Assertions.assertEquals(emp, capi.updateEmployee(emp, 1L));
    }

    @Test
    @Order(4)
    public void deleteEmployeeTest(){
        when(erep.findById(any(Long.class))).thenReturn(Optional.ofNullable(emp));
        Assertions.assertEquals(ResponseEntity.ok("Record deleted"), capi.deleteEmployee(1L));
    }
}
