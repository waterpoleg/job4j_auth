package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
