package com.mybatis.mapper;


import com.mybatis.bean.Employee;

public interface EmployeeMapper {

    Employee selectEmpById(Integer id);
}
