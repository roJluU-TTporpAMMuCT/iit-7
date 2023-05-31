package org.negro.labcloud.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.negro.labcloud.models.Company;
import org.negro.labcloud.repos.CompanyRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CompanyController {

    private CompanyRepo crep;

    @GetMapping("/companies")
    public List<Company> getCompanies(){
        return crep.findAll();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id){
        Company comp = crep.findById(id).orElse(null);
        return comp != null ? ResponseEntity.ok(comp) : ResponseEntity.notFound().build();
    }

    @PostMapping("/company")
    public Company createCompany(@Valid Company comp){
        return crep.save(comp);
    }

    @PatchMapping("/company/{id}")
    public Company updateCompany(@Valid Company comp, @PathVariable Long id){
        comp.setId(id);
        return crep.findById(id).isEmpty() ? null : crep.save(comp);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        ResponseEntity<String> res = crep.findById(id).isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok("Record deleted");
        crep.deleteById(id);
        return res;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
