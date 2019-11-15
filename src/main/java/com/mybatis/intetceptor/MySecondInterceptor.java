package com.mybatis.intetceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

@Intercepts(
        {@Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class)}
)
public class MySecondInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MySecondInterceptor...intercept...：" + invocation.getMethod());
        Object proceed = invocation.proceed();
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("MySecondInterceptor...plugin...MyBatis 将要包装的对象: " + target);
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("MySecondInterceptor...properties" + properties);
    }
}
