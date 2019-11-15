package com.mybatis.typehandler;

import com.mybatis.bean.EmpStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义枚举类型处理器, 实现 TypeHandler 接口，或者继承 BaseTypeHandler
 */
public class EmpStatusTypeHandler implements TypeHandler<EmpStatus> {
    /**
     * 定义当前数据如何保存到数据库中
     * @param preparedStatement
     * @param i
     * @param empStatus
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, EmpStatus empStatus, JdbcType jdbcType) throws SQLException {
        System.out.println("要保存的状态码：" + empStatus.getCode());
        preparedStatement.setInt(i, empStatus.getCode());
    }

    /**
     * 根据从数据库中拿到的枚举状态码返回一个枚举对象
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public EmpStatus getResult(ResultSet resultSet, String columnName) throws SQLException {
        int code = resultSet.getInt(columnName);
        System.out.println("从数据库中获取的状态码：" + code);
        return EmpStatus.getEmpStatusByCode(code);
    }

    @Override
    public EmpStatus getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int code = resultSet.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码：" + code);
        return EmpStatus.getEmpStatusByCode(code);
    }

    @Override
    public EmpStatus getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        int code = callableStatement.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码：" + code);
        return EmpStatus.getEmpStatusByCode(code);
    }
}
