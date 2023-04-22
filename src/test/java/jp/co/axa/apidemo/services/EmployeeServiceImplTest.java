package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.models.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @Before
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .name("Aja")
                .salary(1250)
                .department("Product")
                .build();
    }

    @Test
    public void retrieveEmployees() {

    }

    // JUnit test for retrieveEmployees method
    @Test
    public void givenEmployeesListWhenGetAllEmployeesThenReturnEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .name("Mohammad")
                .salary(1230)
                .department("Product")
                .build();

        given(employeeRepository.findAllEmployees(PageRequest.of(0, 5))).willReturn(Arrays.asList(employee, employee1));
        // when
        List<Employee> employeeList = employeeService.retrieveEmployees(0, 5);
        // then - verify the output
        assertNotNull(employeeList);
        assertThat(employeeList, hasSize(2));
    }
}