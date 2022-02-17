package com.tutorial.example2.service;

import com.tutorial.example2.entity.CourseRegistration;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.example2.entity.Student;

import java.util.List;


@Transactional
public class StudentService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void saveStudent(Student student){
        sessionFactory.getCurrentSession().save(student);
    }

    public void updateStudent(Student student){
        sessionFactory.getCurrentSession().update(student);
    }
    
    public List<Student> getAllStudents(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Student.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // not return duplicate records of students
        return criteria.list();
    }

    public void deleteStudent(Student student){
        sessionFactory.getCurrentSession().delete(student);

        // delete all registration of this student from courses that the student enrolled
        for (CourseRegistration registration : student.getCourseRegistrations()) {
            registration.getCourse().deleteCourseRegistration(registration);
        }
    }

    public Student getStudentById(int id){
        return (Student)sessionFactory.getCurrentSession().get(Student.class, id);
    }

    public List<Student> getStudentByName(String name){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Student.class);
        criteria.add(Restrictions.like("name",name, MatchMode.ANYWHERE));
        return criteria.list();
    }
}

