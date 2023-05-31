package org.negro.labcloud.apis;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.negro.labcloud.controllers.CompanyController;
import org.negro.labcloud.models.Company;
import org.negro.labcloud.repos.CompanyRepo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {
    @MockBean
    private CompanyRepo crep;

    @InjectMocks
    private CompanyController capi;

    private Company comp = Company.builder()
            .id(1L)
            .name("test")
            .headquarters("test place")
            .build();

    @Test
    @Order(1)
    public void saveCompanyTest(){
        when(crep.save(any(Company.class))).thenReturn(comp);
        Assertions.assertEquals(comp, capi.createCompany(comp));
    }

    @Test
    @Order(2)
    public void getCompanyTest(){
        when(crep.findById(any(Long.class))).thenReturn(Optional.ofNullable(comp));
        Assertions.assertEquals(ResponseEntity.ok(comp), capi.getCompany(1L));
    }

    @Test
    @Order(3)
    public void updateCompanyTest(){
        when(crep.findById(any(Long.class))).thenReturn(Optional.ofNullable(comp));
        when(crep.save(any(Company.class))).thenReturn(comp);
        Assertions.assertEquals(comp, capi.updateCompany(comp, 1L));
    }

    @Test
    @Order(4)
    public void deleteCompanyTest(){
        when(crep.findById(any(Long.class))).thenReturn(Optional.ofNullable(comp));
        Assertions.assertEquals(ResponseEntity.ok("Record deleted"), capi.deleteCompany(1L));
    }
}
