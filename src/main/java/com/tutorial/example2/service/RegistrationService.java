package com.tutorial.example2.service;

import com.tutorial.example2.config.AppConfig;
import com.tutorial.example2.entity.Course;
import com.tutorial.example2.entity.CourseRegistration;
import com.tutorial.example2.entity.Student;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class RegistrationService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveRegistration(CourseRegistration registration) {
        sessionFactory.getCurrentSession().save(registration);
    }

    public List<CourseRegistration> getRegistrations(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CourseRegistration.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // not return duplicate records of students
        return criteria.list();
    }

    public void dropCourseRegistration(CourseRegistration registration, AnnotationConfigApplicationContext context) {
        Student student = registration.getStudent();
        Course course = registration.getCourse();

        if(!context.containsBean(student.getName())) return;
        student.deleteCourseRegistration(registration);
        if(!context.containsBean(course.getName())) return;
        course.deleteCourseRegistration(registration);

        sessionFactory.getCurrentSession().delete(registration);
//        registration = null;
    }
}
