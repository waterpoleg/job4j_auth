package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.model.Employee;
import ru.job4j.model.Person;
import ru.job4j.service.EmployeeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    private final RestTemplate restTemplate;
    private final EmployeeService employeeService;

    @GetMapping("/")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                employeeService.save(employee),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{id}/account/")
    public ResponseEntity<Employee> addAccount(@PathVariable("id") int id,
                                               @RequestBody Person person) {
        Employee employee = employeeService.findById(id).orElse(null);
        if (employee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Person personCreated = restTemplate.postForObject(API, person, Person.class);
        employee.addAccount(personCreated);
        employeeService.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/account/")
    public ResponseEntity<Void> updateAccount(@PathVariable("id") int id,
                                              @RequestBody Person person) {
        Employee employee = employeeService.findById(id).orElse(null);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        restTemplate.put(API, person, Person.class);
        employee.addAccount(person);
        employeeService.save(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/account/{accId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") int id,
                                              @PathVariable("accId") int accId) {
        Employee employee = employeeService.findById(id).orElse(null);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        restTemplate.delete(API_ID, accId);
        return ResponseEntity.ok().build();
    }

}
