package jp.co.axa.apidemo.models.dtos;

import jp.co.axa.apidemo.models.Employee;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class EmployeeDto {
    @NotEmpty(message = "The full name is required.")
    private String name;
    @Min(0)
    private Integer salary;
    @NotEmpty(message = "The department name is required.")
    private String department;

    public Employee ToEmployee(){
        return Employee.builder()
                .name(getName())
                .salary(getSalary())
                .department(getDepartment())
                .build();
    }
}
