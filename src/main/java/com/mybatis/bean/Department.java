package com.mybatis.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 部门
 */
@Data
public class Department implements Serializable {

    private Integer id;
    private String departmentName;
    private List<Employee> employees;

    public Department(Integer id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

}
