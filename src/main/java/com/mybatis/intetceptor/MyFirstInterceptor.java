package com.mybatis.intetceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Statement;
import java.util.Properties;

/**
 * 自定义插件，实现 mybatis 的 Interceptor
 *
 * @Intercepts : 完成插件签名，告诉 MyBatis 当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class)
        })
public class MyFirstInterceptor implements Interceptor {

    /**
     * intercept: 拦截目标对象的目标方法的执行
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("MyFirstInterceptor...intercept...: " + invocation.getMethod());

        //动态的改变一下sql运行的参数：以前1号员工，实际从数据库查询5号员工
        Object target = invocation.getTarget();
        System.out.println("当前拦截到的对象："+target);

        //拿到：StatementHandler ==> ParameterHandler ===> parameterObject
        //拿到 target 的元数据
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句用的参数是：" + value);

        //修改完sql语句要用的参数
        metaObject.setValue("parameterHandler.parameterObject", 5);

        // 执行目标方法
        Object proceed = invocation.proceed();

        // 返回执行后的返回值
        return proceed;
    }

    /**
     * plugin: 包装目标对象，为目标对象创建一个代理对象
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("MyFirstInterceptor...plugin...:MyBatis 将要包装的对象 " + target);
        // 可以借助 Plugin 的 wrap 方法来使用当前的 Interceptor 包装目标对象
        Object wrap = Plugin.wrap(target, this);

        // 返回为当前 target 创建的动态代理
        return wrap;
    }

    /**
     * setProperties：将插件注册时的 property 属性设置进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("MyFirstInterceptor...setProperties");
        System.out.println("插件配置的信息： " + properties);
    }
}
