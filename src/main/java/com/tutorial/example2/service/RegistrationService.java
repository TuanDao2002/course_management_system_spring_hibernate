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

        // retrieve objects from session
        Student studentInSession = studentService.getStudentById(student.getId());
        Course courseInSession = courseService.getCourseById(course.getId());

        // check if they exist or not
        boolean hasStudent = studentInSession != null;
        boolean hasCourse =  courseInSession != null;

        // if they exist, delete the registration in each of them and evict them from session to prevent error
        if (hasStudent) {
            student.deleteCourseRegistration(registration);
            sessionFactory.getCurrentSession().evict(studentInSession);
        }

        if (hasCourse) {
            course.deleteCourseRegistration(registration);
            sessionFactory.getCurrentSession().evict(courseInSession);
        }

        if (hasStudent && hasCourse) {
            sessionFactory.getCurrentSession().delete(registration);
        }

    }
}
