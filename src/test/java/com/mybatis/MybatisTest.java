package com.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatis.bean.Department;
import com.mybatis.bean.EmpStatus;
import com.mybatis.bean.Employee;
import com.mybatis.mapper.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

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

    /**
     * sql 接口映射
     */
    @Test
    public void test02 () {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
            Employee emp = mapper.getEmpById(1);
            System.out.println(emp);
        }
    }

    /**
     * 测试增删改
     * 1. mybatis 允许增删改直接定义一下类型返回值
     *      Integer、Long、Boolean、void
     * 2. 需要手动提交数据
     *      sqlSessionFactory.openSession() ==》手动
     *      sqlSessionFactory.openSession(true) ==》自动
     */
    @Test
    public void test03 () {
        // 获取到的 SqlSession 不会自动提交数据
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            // 1. 添加
//            Employee employee = new Employee(null, "Jack", "0", "jack@163.com");
//            mapper.insertEmp(employee);
//            System.out.println(employee);

            // 2. 修改
//            Employee employee = new Employee(3, "Rose", "1", "rose@163.com");
//            int i = mapper.updateEmp(employee);
//            System.out.println(i);

            // 3. 删除
//            boolean b = mapper.deleteEmp(3);
//            System.out.println(b);

            // 4. 条件查询
            Employee tom = mapper.getEmpByIdAndLastName(1, "Tom");
            System.out.println(tom);

            System.out.println("=============");
            Map<String, Object> map = new HashMap<>();
            map.put("id", 2);
            map.put("lastName", "Jerry");
            Employee jerry = mapper.getEmpByMap(map);
            System.out.println(jerry);

            System.out.println("==模糊查询==");
            // 5. 模糊查询
            List<Employee> emps = mapper.getEmpNameLike("%J%");
            System.out.println(emps);

            // 6. 封装一条记录返回 Map
            System.out.println("==封装一条记录返回 Map==");
            Map<String, Object> returnMap = mapper.getEmpByIdReturnMap(1);
            System.out.println(returnMap);

            // 7. 返回 Map 的多条记录
            System.out.println("===返回 Map 的多条记录 ==");
            Map<Integer, Employee> mapList = mapper.getEmpByLastNameLikeReturnMap("%J%");
            System.out.println(mapList);

            // 手动提交数据
            sqlSession.commit();
        }
    }

    @Test
    public void test04 () {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmployeePlusMapper mapper = sqlSession.getMapper(EmployeePlusMapper.class);

//            Employee emp = mapper.getEmpById(1);
//            System.out.println(emp);
//
//            System.out.println("==========");
//            Employee empAndDept = mapper.getEmpAndDept(1);
//            System.out.println(empAndDept);

            System.out.println("-----------");
            Employee empByIdStep = mapper.getEmpByIdStep(2);
            System.out.println(empByIdStep);
            System.out.println(empByIdStep.getDepartment());
        }
    }

    @Test
    public void test05 () {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
//            Department dept = mapper.getDeptByIdPlus(1);
//            System.out.println(dept);

            System.out.println("=============");
            Department deptByIdStep = mapper.getDeptByIdStep(1);
            // lazy sql 分开打印
            System.out.println(deptByIdStep.getDepartmentName());
            System.out.println(deptByIdStep.getEmployees());
        }
    }

    /**
     * 查询的时候如果某些条件没带可能 sql 拼装会有问题
     *  1、给where后面加上 1=1，以后的条件都 and xxx.
     * 	2、mybatis使用 where标签来将所有的查询条件包括在内。
     * 	    mybatis就会将where标签中拼装的sql，多出来的 and 或者 or去掉
     * 		where只会去掉第一个多出来的 and 或者 or。
     */
    @Test
    public void test06 () {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmployeeDynamicSQLMapper mapper = sqlSession.getMapper(EmployeeDynamicSQLMapper.class);
            Employee emp = new Employee(1, "Admin", "0", "");
//            List<Employee> emps = mapper.getEmpsByConditionIf(emp);
//            System.out.println(emps);

//            System.out.println("=========");
//            List<Employee> emps2 = mapper.getEmpsByConditionTrim(emp);
//            System.out.println(emps2);

//            List<Employee> chooseEmp = mapper.getEmpsByConditionChoose(emp);
//            System.out.println(chooseEmp);

//            mapper.updateEmp(emp);

            // foreach
            List<Employee> empsByConditionForEach = mapper.getEmpsByConditionForEach(Arrays.asList(1, 2, 5));
            System.out.println(empsByConditionForEach);

            sqlSession.commit();
        }
    }

    @Test
    public void test07 () {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            EmployeeDynamicSQLMapper mapper = sqlSession.getMapper(EmployeeDynamicSQLMapper.class);
            List<Employee> emps = new ArrayList<>();
            Employee employee = new Employee(null, "AA2", "0", "aa@163.com", new Department(1, "开发部"));
            Employee employee1 = new Employee(null, "BB2", "1", "bb@163.com", new Department(2, "测试部"));
            Employee employee2 = new Employee(null, "CC2", "0", "cc@163.com", new Department(1, "开发部"));
            emps.add(employee);
            emps.add(employee1);
            emps.add(employee2);
            mapper.addEmps(emps);

            sqlSession.commit();
        }
    }

    @Test
    public void test08 () {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeDynamicSQLMapper mapper = sqlSession.getMapper(EmployeeDynamicSQLMapper.class);
            List<Employee> emps = mapper.getEmpsTestInnerParameter(new Employee(null, "c", null, null));
            System.out.println(emps);
        }
    }

    /**
     * 两级缓存：
     * 一级缓存：（本地缓存）：sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个 Map
     * 		与数据库同一次会话期间查询到的数据会放在本地缓存中。
     * 		以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
     *
     * 		一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
     * 		1、sqlSession不同。
     * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
     * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
     * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
     *
     * 二级缓存：（全局缓存）：基于 namespace 级别的缓存：一个 namespace 对应一个二级缓存：
     * 		工作机制：
     * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
     * 		3、sqlSession===EmployeeMapper==>Employee
     * 						DepartmentMapper===>Department
     * 			不同 namespace 查出的数据会放在自己对应的缓存中（map）
     * 			效果：数据会从二级缓存中获取
     * 				查出的数据都会被默认先放在一级缓存中。
     * 				只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
     * 		使用：
     * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     * 			2）、去 mapper.xml 中配置使用二级缓存：<cache></cache>
     * 			3）、我们的 POJO 需要实现序列化接口
     *
     * 和缓存有关的设置/属性：
     * 			1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
     * 			2）、每个 select 标签都有 useCache="true"：
     * 					false：不使用缓存（一级缓存依然使用，二级缓存不使用）
     * 			3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
     * 					增删改执行完成后就会清除缓存；
     * 					测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     * 					查询标签：flushCache="false"：
     * 						如果 flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     * 			4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
     * 			5）、localCacheScope：本地缓存作用域：（一级缓存 SESSION）；当前会话的所有数据保存在会话缓存中；
     * 								STATEMENT：可以禁用一级缓存；
     *
     *第三方缓存整合：
     *		1）、导入第三方缓存包即可；
     *		2）、导入与第三方缓存整合的适配包；官方有；
     *		3）、mapper.xml中使用自定义缓存
     *		<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
     *
     * @throws IOException
     *
     */
    @Test
    public void testFirstLevelCache () {
        try(SqlSession sqlSession = sqlSessionFactory.openSession();
            SqlSession sqlSession1 = sqlSessionFactory.openSession()){
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.selectEmpById(1);
            System.out.println(employee);

//            EmployeeMapper mapper1 = sqlSession1.getMapper(EmployeeMapper.class);
//            mapper.insertEmp(new Employee(null, "testCache", null, null));
//            sqlSession.clearCache();

            Employee employee1 = mapper.selectEmpById(1);
            System.out.println(employee1);
            System.out.println(employee == employee1);
        }
    }
    @Test
    public void testSecondLevelCache () {
        try(SqlSession sqlSession = sqlSessionFactory.openSession();
            SqlSession sqlSession1 = sqlSessionFactory.openSession()){
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee emp = mapper.selectEmpById(1);
            System.out.println(emp);

            sqlSession.close();
            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
            EmployeeMapper mapper1 = sqlSession1.getMapper(EmployeeMapper.class);
            Employee emp1 = mapper1.selectEmpById(1);
            System.out.println(emp1);

        }
    }

    @Test
    public void testSecondLevelCache2 () {
        try(SqlSession sqlSession = sqlSessionFactory.openSession();
            SqlSession sqlSession1 = sqlSessionFactory.openSession()){
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            Department dept = mapper.getDeptById(1);
            System.out.println(dept);

            sqlSession.close();
            DepartmentMapper mapper1 = sqlSession1.getMapper(DepartmentMapper.class);
            Department dept1 = mapper1.getDeptById(1);
            System.out.println(dept1);
        }
    }

    @Test
    public void testPageHelper () {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Page<Object> page = PageHelper.startPage(1, 5);
            List<Employee> emps = mapper.getEmps();
            System.out.println(emps);
/*

            System.out.println("当前页码：" + page.getPageNum());
            System.out.println("总记录数：" + page.getTotal());
            System.out.println("每页的记录数：" + page.getPageSize());
            System.out.println("总页码：" + page.getPages());
*/

            PageInfo<Employee> info = new PageInfo<>(emps, 5);
            System.out.println("当前页码：" + info.getPageNum());
            System.out.println("总记录数：" + info.getTotal());
            System.out.println("每页的记录数："+info.getPageSize());
            System.out.println("总页码："+ info.getPages());
            System.out.println("是否第一页：" + info.isIsFirstPage());
            System.out.println("连续显示的页码：");
            int[] nums = info.getNavigatepageNums();
            for (int num : nums) {
                System.out.println(num);
            }
        }
    }

    /**
     * 批量：（预编译sql一次==>设置参数===>10000次===>执行（1次））
     * Parameters: 616c1(String), b(String), 1(String)==>4598
     * 非批量：（预编译sql=设置参数=执行）==》10000    10200
     */
    @Test
    public void testBatch () {
        //可以执行批量操作的sqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)){
            long start = System.currentTimeMillis();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            for (int i=0; i<5; i++) {
                mapper.insertEmp(new Employee(null, UUID.randomUUID().toString().substring(0, 5), "0", "aa@163.com"));
            }
            sqlSession.commit();
            long end = System.currentTimeMillis();
            System.out.println("总时长(ms)：" + (end - start));
        }
    }

    @Test
    public void testEnumUse(){
        EmpStatus login = EmpStatus.LOGIN;
        System.out.println("枚举的索引：" + login.ordinal());
        System.out.println("枚举的名字：" + login.name());

        System.out.println("枚举的状态码：" + login.getCode());
        System.out.println("枚举的提示消息："+ login.getMsg());
    }

    /**
     * 默认 mybatis 在处理枚举对象的时候保存的是枚举的名字：EnumTypeHandler
     * 改变使用：EnumOrdinalTypeHandler：
     * @throws IOException
     */
    @Test
    public void testEnum () {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//            Employee employee = new Employee(null, "Sanae", "1", "sanae@163.com", EmpStatus.LOGIN);
//            mapper.insertEmp(employee);
//            System.out.println("保存成功，id = " + employee.getId());
//            sqlSession.commit();

            Employee emp = mapper.selectEmpById(21);
            System.out.println(emp.getEmpStatus());
        }

    }
}
