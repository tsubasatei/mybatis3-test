package com.mybatis.controller;

import com.mybatis.bean.Employee;
import com.mybatis.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emps")
    public String list(Map<String, Object> map) {
        List<Employee> emps = employeeService.getEmps();
        map.put("emps", emps);
        return "list";
    }
}
