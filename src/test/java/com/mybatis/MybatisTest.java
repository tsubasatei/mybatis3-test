package com.mybatis;

import com.mybatis.bean.Employee;
import com.mybatis.mapper.EmployeeMapper;
import com.mybatis.mapper.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 1. 接口式编程
 *      原生：		Dao		====>  DaoImpl
 * 	    mybatis：	Mapper	====>  xxMapper.xml
 *
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 *
 * 3、SqlSession 和 connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 *
 * 4、mapper 接口没有实现类，但是 mybatis会为这个接口生成一个代理对象。（将接口和 xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 *
 * 5、两个重要的配置文件：
 * 		mybatis 的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql 映射文件：保存了每一个sql语句的映射信息：将 sql 抽取出来。
 */
public class MybatisTest {


    private SqlSessionFactory sqlSessionFactory;

    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
     *      1）、有数据源一些运行环境信息
     *      2）、sql 映射文件：配置了每一个 sql，以及 sql 的封装规则等
     *      3）、将 sql 映射文件注册在全局配置文件中
     *      4）、写代码：根据全局配置文件得到 SqlSessionFactory
     * 2. 获取 SqlSession 实例，能直接执行已经映射好的 sql 语句
     *      sql 的唯一标识：告诉 Mybatis 执行哪个 sql，sql 都是保存在映射文件中
     *      执行 sql 要用的参数
     *    一个 SqlSession 就是代表和数据库的一次会话，用完关闭
     * @throws IOException
     */
    @Test
    public void test (){

/*
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Employee employee = sqlSession.selectOne("com.mybatis.mapper.EmployeeMapper.selectEmpById", 1);
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
*/

        /*try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Employee employee = sqlSession.selectOne("com.mybatis.mapper.EmployeeMapper.selectEmpById", 1);
            System.out.println(employee);
        }*/

        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            /**
             * org.apache.ibatis.binding.MapperProxy@67c33749
             * Mybatis 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
             */
            System.out.println(mapper);

            Employee employee = mapper.selectEmpById(1);
            System.out.println(employee);
        }
    }

    @Test
    public void test02 () {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
        Employee emp = mapper.getEmpById(1);
        System.out.println(emp);

    }
}
