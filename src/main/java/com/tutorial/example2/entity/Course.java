package com.tutorial.example2.entity;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    // delete the joined record and related foreign keys in records of other table
    // @OnDelete(action = OnDeleteAction.CASCADE) -> alternate implementation
    @Cascade({CascadeType.DETACH, CascadeType.DELETE})
    private List<CourseRegistration> courseRegistrations;

	public Course() {
        courseRegistrations = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    public void addCourseRegistration(CourseRegistration registration) {
        courseRegistrations.add(registration);
    }

    public void deleteCourseRegistration(CourseRegistration registration) {
        courseRegistrations.remove(registration);
    }

    public void deleteAllRegistrations() {
        courseRegistrations.clear();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}