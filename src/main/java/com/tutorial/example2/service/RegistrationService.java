package com.tutorial.example2.service;

import com.tutorial.example2.entity.Course;
import com.tutorial.example2.entity.CourseRegistration;
import com.tutorial.example2.entity.Student;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public CourseRegistration getCourseRegistrationById(int id) {
        return sessionFactory.getCurrentSession().get(CourseRegistration.class, id);
    }

    public void dropCourseRegistration(CourseRegistration registration, StudentService studentService, CourseService courseService) {
        Student student = registration.getStudent();
        Course course = registration.getCourse();

        // check if application context has Student and Course objects
        boolean hasStudent = studentService.getStudentById(student.getId()) != null;
        boolean hasCourse = courseService.getCourseById(course.getId()) != null;

        if (hasStudent) {
            student.deleteCourseRegistration(registration);
        }

        if (hasCourse) {
            course.deleteCourseRegistration(registration);
        }

        // can only delete a persistent object to prevent error of different identifier with the same value in the session
        if (hasStudent && hasCourse) {
            sessionFactory.getCurrentSession().delete(this.getCourseRegistrationById(registration.getId()));
        }

    }
}
