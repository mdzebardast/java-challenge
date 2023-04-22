package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.models.Employee;
import jp.co.axa.apidemo.models.dtos.EmployeeDto;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
                                                          @RequestParam(defaultValue = "5") @Max(20) Integer pageSize) {
        log.info("Request for all employees for page No. of {} and size of {}", pageNo, pageSize);
        return new ResponseEntity<List<Employee>>(employeeService.retrieveEmployees(pageNo, pageSize), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public void saveEmployee(@Valid EmployeeDto employee){
        employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        Employee emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            employeeService.updateEmployee(employee);
        }

    }

}
