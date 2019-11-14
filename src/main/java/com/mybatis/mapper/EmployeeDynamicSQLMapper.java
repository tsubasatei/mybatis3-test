package com.mybatis.mapper;


import com.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDynamicSQLMapper {

    List<Employee> getEmpsByConditionIf(Employee employee);

    List<Employee> getEmpsByConditionTrim(Employee employee);

    List<Employee> getEmpsByConditionChoose(Employee employee);

    void updateEmp(Employee employee);

    List<Employee> getEmpsByConditionForEach(@Param("ids") List<Integer> ids);

    void addEmps(@Param("emps") List<Employee> emps);

    List<Employee> getEmpsTestInnerParameter(Employee employee);
}
