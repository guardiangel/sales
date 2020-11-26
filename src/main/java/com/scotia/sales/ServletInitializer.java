package com.scotia.sales;

    import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author
 *      Felix
 * 使用外置的tomcat启动，则要继承SpringBootServletInitializer类，复写configure方法。使用的是War包方式
 * 如使用内置的tomcat启动，则无需此类，使用的是JAR包方式。
 * jar包和war包启动区别
 *
 *     jar包:执行SpringBootApplication的run方法,启动IOC容器,然后创建嵌入式Servlet容器
 *
 * 　war包:  先是启动Servlet服务器,服务器启动Springboot应用(springBootServletInitizer),然后启动IOC容器
 *
 * SpringBootServletInitializer实例执行onStartup方法的时候会通过createRootApplicationContext方法来执行run方法，接下来的过程就同以jar包形式启动的应用的run过程一样了，在内部会创建IOC容器并返回，只是以war包形式的应用在创建IOC容器过程中，不再创建Servlet容器了。
 * SpringBootServletInitializer的执行过程，简单来说就是通过SpringApplicationBuilder构建并封装SpringApplication对象，并最终调用SpringApplication的run方法的过程。
 * <p>
 * spring boot就是为了简化开发的，也就是用注解的方式取代了传统的xml配置。
 * <p>
 * SpringBootServletInitializer就是原有的web.xml文件的替代。
 * <p>
 * 使用了嵌入式Servlet,默认是不支持jsp。
 * <p>
 * SpringBootServletInitializer 可以使用外部的Servlet容器，使用步骤：
 * <p>
 * 1.必须创建war项目，需要创建好web项目的目录。2.嵌入式Tomcat依赖scope指定provided。3.编写SpringBootServletInitializer类子类,并重写configure方法。
 * 2020-11-14 15:31:22 SGQ
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SalesApplication.class);
    }

}
