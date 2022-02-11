package com.tutorial.example2.config;

import java.util.Properties;

import com.tutorial.example2.entity.CourseRegistration;
import com.tutorial.example2.service.RegistrationService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tutorial.example2.entity.Course;
import com.tutorial.example2.entity.Student;
import com.tutorial.example2.service.CourseService;
import com.tutorial.example2.service.StudentService;


@Configuration
@EnableTransactionManagement
public class AppConfig {

	@Bean
    public Student student(){
        return new Student();
    }
	
	@Bean
    public Course course(){
        return new Course();
    }

    @Bean
    public CourseRegistration courseRegistration(Student student, Course course) { return new CourseRegistration(student, course); }
	
    @Bean
    public StudentService studentService(){
        return new StudentService();
    }
    
    @Bean
    public CourseService courseService(){
        return new CourseService();
    }

    @Bean
    public RegistrationService registrationService() {
        return new RegistrationService();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create-drop");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        //To use postgresql
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("khatun");


        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.setPackagesToScan("com.tutorial.example2.entity");


        return  sessionFactoryBean;
    }


    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);
        return tx;
    }


}
