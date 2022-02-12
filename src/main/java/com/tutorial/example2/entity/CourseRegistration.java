package com.tutorial.example2.entity;

import javax.persistence.*;

@Entity
public class CourseRegistration {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

    public CourseRegistration() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentAndCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
        if (student != null) {
            student.addCourseRegistration(this);
        }

        if (course != null) {
            course.addCourseRegistration(this);
        }
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "CourseRegistration{" +
                "id=" + id +
                ", course=" + course +
                ", studentID=" + getStudent().getId() +
                '}';
    }
}
