package com.mybatis.mapper;


import com.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    Employee selectEmpById(Integer id);

    int insertEmp(Employee employee);

    int updateEmp(Employee employee);

    boolean deleteEmp(Integer id);

    Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String  lastName);

    Employee getEmpByMap(Map<String, Object> map);

    List<Employee> getEmpNameLike(String lastName);

    // 将一条记录封装成 Map 类型：key就是列名，值就是对应的值
    Map<String, Object> getEmpByIdReturnMap(Integer id);

    /**
     * 多条记录封装一个 map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的 javaBean
     * 	@MapKey: 告诉 mybatis 封装这个 map的时候使用哪个属性作为 map 的 key
     * @param lastName
     * @return
     */
    @MapKey("id")
    Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);
}
