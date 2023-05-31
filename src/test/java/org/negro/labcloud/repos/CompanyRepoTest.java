package org.negro.labcloud.repos;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.negro.labcloud.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyRepoTest {

    @Autowired
    private CompanyRepo crep;

    private static Long userId;

    @Test
    @Order(1)
    @Rollback(false)
    public void saveCompanyTest(){
        Company comp = Company.builder()
                .name("Coal Ink")
                .headquarters("14 Joint St, Indianapolis, IN, US")
                .build();

        crep.save(comp);
        userId = comp.getId();
        Assertions.assertEquals(true, userId != null);
    }

    @Test
    @Order(2)
    @Rollback(false)
    public void getCompanyTest(){
        Assertions.assertEquals(userId, crep.findById(userId).get().getId());
    }

    @Test
    @Order(3)
    @Rollback(false)
    public void getCompaniesTest(){
        Assertions.assertEquals(true, crep.findAll().size() > 0);
    }

    @Test
    @Order(4)
    @Rollback
    public void updateCompanyTest(){
        Company comp = crep.findById(userId).get();
        comp.setName("Longsale");
        crep.save(comp);
        Assertions.assertEquals("Longsale", crep.findById(userId).get().getName());
    }

    @Test
    @Order(5)
    @Rollback(false)
    public void deleteCompanyTest(){
        crep.deleteById(userId);
        Assertions.assertEquals(null, crep.findById(userId).orElse(null));
    }


}
