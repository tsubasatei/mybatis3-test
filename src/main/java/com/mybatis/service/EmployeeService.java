package com.mybatis.service;


import com.mybatis.bean.Employee;
import com.mybatis.mapper.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Autowired
    private SqlSession sqlSession;
    

    public List<Employee> getEmps() {

//        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        return employeeMapper.getEmps();
    }
}
