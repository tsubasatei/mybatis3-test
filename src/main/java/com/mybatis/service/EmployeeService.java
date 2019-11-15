package com.mybatis.service;


import com.mybatis.bean.Employee;
import com.mybatis.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getEmps() {
        return employeeMapper.getEmps();
    }
}
