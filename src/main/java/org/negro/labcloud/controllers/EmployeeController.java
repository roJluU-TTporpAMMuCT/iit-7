package org.negro.labcloud.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.negro.labcloud.models.Employee;
import org.negro.labcloud.models.Employee;
import org.negro.labcloud.repos.EmployeeRepo;
import org.negro.labcloud.repos.EmployeeRepo;
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
public class EmployeeController {

    private EmployeeRepo erep;

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        return erep.findAll();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
        Employee emp = erep.findById(id).orElse(null);
        return emp != null ? ResponseEntity.ok(emp) : ResponseEntity.notFound().build();
    }

    @PostMapping("/employee")
    public Employee createEmployee(@Valid Employee emp){
        return erep.save(emp);
    }

    @PatchMapping("/employee/{id}")
    public Employee updateEmployee(@Valid Employee emp, @PathVariable Long id){
        emp.setId(id);
        return erep.findById(id).isEmpty() ? null : erep.save(emp);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        ResponseEntity<String> res = erep.findById(id).isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok("Record deleted");
        erep.deleteById(id);
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
