package org.negro.labcloud.repos;

import org.junit.jupiter.api.*;
import org.negro.labcloud.models.Company;
import org.negro.labcloud.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo erep;

    @Autowired
    private CompanyRepo crep;

    private static Long userId;

    private Company comp;

    @Test
    @Order(1)
    @Rollback(false)
    public void saveEmployeeTest(){
        comp = crep.findAll().get(0);
        Employee emp = Employee.builder()
                        .firstname("John")
                        .lastname("Doe")
                        .phone("2554226745")
                        .position("cleaner")
                        .company(comp)
                        .build();

        erep.save(emp);
        userId = emp.getId();
        Assertions.assertEquals(true, userId != null);
    }

    @Test
    @Order(2)
    public void getEmployeeTest(){
        Assertions.assertEquals(userId, erep.findById(userId).get().getId());
    }

    @Test
    @Order(3)
    public void getEmployeesTest(){
        Assertions.assertEquals(true, erep.findAll().size() > 0);
    }

    @Test
    @Order(4)
    @Rollback(false)
    public void updateEmployeeTest(){
        Employee emp = erep.findById(userId).get();
        emp.setFirstname("Longsale");
        erep.save(emp);
        Assertions.assertEquals("Longsale", erep.findById(userId).get().getFirstname());
    }

    @Test
    @Order(5)
    @Rollback(false)
    public void deleteEmployeeTest(){
        erep.deleteById(userId);
        Assertions.assertEquals(null, erep.findById(userId).orElse(null));
    }


}
