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

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public CourseRegistration(Student student, Course course){
        this.student = student;
        this.course = course;

        student.addCourseRegistration(this);
        course.addCourseRegistration(this);
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
