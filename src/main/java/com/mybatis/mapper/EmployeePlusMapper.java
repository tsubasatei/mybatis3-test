package com.mybatis.mapper;


import com.mybatis.bean.Employee;

import java.util.List;

public interface EmployeePlusMapper {

    Employee getEmpById(Integer id);

    Employee getEmpAndDept(Integer id);

    Employee getEmpByIdStep(Integer id);

    List<Employee> getEmpsByDeptId(Integer deptId);
}
