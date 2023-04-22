package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.models.Employee;
import jp.co.axa.apidemo.models.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public List<Employee> retrieveEmployees(Integer pageNo, Integer pageSize);

    public Employee getEmployee(Long employeeId);

    public void saveEmployee(EmployeeDto employee);

    public void deleteEmployee(Long employeeId);

    public void updateEmployee(Employee employee);
}