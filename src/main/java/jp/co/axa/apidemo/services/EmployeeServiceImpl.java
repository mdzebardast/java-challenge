package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees(Integer pageNo, Integer pageSize) {

        List<Employee> employees = employeeRepository.findAllEmployees(PageRequest.of(pageNo, pageSize));
        log.info("Retrieve {} of employees", employees.size());
        return employees;
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.get();
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
        log.info("Employee {} has been saved successfully", employee.getName());
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
        log.info("Employee with id {} has been deleted", employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
        log.info("Employee with id {} has been updated", employee.getId());
    }
}