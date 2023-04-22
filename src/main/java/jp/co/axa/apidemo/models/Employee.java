package jp.co.axa.apidemo.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name="EMPLOYEE_NAME")
    private String name;
    @Column(name="EMPLOYEE_SALARY")
    private Integer salary;
    @Column(name="DEPARTMENT")
    private String department;

}
