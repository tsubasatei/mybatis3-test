package com.mybatis.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 员工
 * @Alias : 注解为某个类型指定新的别名
 */
//@Alias("employee")
@Data
@NoArgsConstructor
public class Employee implements Serializable {
    private Integer id;
    private String lastName;
    private String gender;
    private String email;

    private Department department;

    private EmpStatus empStatus;

    public Employee(Integer id, String lastName, String gender, String email) {
        this.id = id;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
    }

    public Employee(Integer id, String lastName, String gender, String email, Department department) {
        this.id = id;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.department = department;
    }

    public Employee(Integer id, String lastName, String gender, String email, EmpStatus empStatus) {
        this.id = id;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.empStatus = empStatus;
    }
}
