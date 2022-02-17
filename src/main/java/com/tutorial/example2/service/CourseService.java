package com.tutorial.example2.service;

import com.tutorial.example2.entity.CourseRegistration;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.example2.entity.Course;

import java.util.List;


@Transactional
public class CourseService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void saveCourse(Course course){
        sessionFactory.getCurrentSession().save(course);
    }

    public void updateCourse(Course course){
        sessionFactory.getCurrentSession().update(course);
    }
    
    public List<Course> getAllCourses(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Course.class)
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // not return duplicate records of courses
        return criteria.list();
    }

    public void deleteCourse(Course course){
        sessionFactory.getCurrentSession().delete(course);

        // delete all registrations of this course from students who enrolled it
        for (CourseRegistration registration : course.getCourseRegistrations()) {
            registration.getStudent().deleteCourseRegistration(registration);
        }

    }

    public Course getCourseById(int id){
        return (Course)sessionFactory.getCurrentSession().get(Course.class, id);
    }

    public List<Course> getCourseByName(String name){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Course.class);
        criteria.add(Restrictions.like("name",name, MatchMode.ANYWHERE));
        return criteria.list();
    }
}

