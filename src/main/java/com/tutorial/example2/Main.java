package com.tutorial.example2;

import com.tutorial.example2.config.AppConfig;
import com.tutorial.example2.entity.Course;
import com.tutorial.example2.entity.CourseRegistration;
import com.tutorial.example2.service.RegistrationService;
import com.tutorial.example2.service.StudentService;
import com.tutorial.example2.service.CourseService;
import com.tutorial.example2.entity.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {


    public static void main(String[] args){

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        StudentService studentService = (StudentService) context.getBean("studentService");

        Student student = new Student();
        student.setName("Minh");
        studentService.saveStudent(student);

        Student anotherStudent = new Student();
        anotherStudent.setName("Tuan");
        studentService.saveStudent(anotherStudent);

        CourseService courseService = (CourseService) context.getBean("courseService");

        Course sadi = new Course();
        sadi.setName("Sadi");
        courseService.saveCourse(sadi);

        Course wp = new Course();
        wp.setName("Web programming");
        courseService.saveCourse(wp);

        Course ucd = new Course();
        ucd.setName("UCD");
        courseService.saveCourse(ucd);

        RegistrationService registrationService = (RegistrationService) context.getBean("registrationService");

        CourseRegistration registration1 = new CourseRegistration();
        registration1.setStudentAndCourse(student, sadi);
        registrationService.saveRegistration(registration1);

        CourseRegistration registration2 = new CourseRegistration();
        registration2.setStudentAndCourse(student, wp);
        registrationService.saveRegistration(registration2);

        CourseRegistration registration3 = new CourseRegistration();
        registration3.setStudentAndCourse(anotherStudent, wp);
        registrationService.saveRegistration(registration3);

        CourseRegistration registration4 = new CourseRegistration();
        registration4.setStudentAndCourse(anotherStudent, sadi);
        registrationService.saveRegistration(registration4);

        CourseRegistration registration5 = new CourseRegistration();
        registration5.setStudentAndCourse(anotherStudent, ucd);
        registrationService.saveRegistration(registration5);

        //Try delete the student to see what happens? (Add cascade to handle the problem)
        courseService.deleteCourse(ucd);
        studentService.deleteStudent(anotherStudent);
        registrationService.dropCourseRegistration(registration1, studentService, courseService);

        for (CourseRegistration registration : registrationService.getRegistrations()) {
            System.out.println(registration);
        }

//        for (Course c : courseService.getAllCourses()) {
//            System.out.println(c);
//        }

//        for (Student stu : studentService.getAllStudents()) {
//            System.out.println(stu);
//        }

//        for (Student s : studentService.getAllStudents()) {
//            System.out.println("ID: " + s.getId() + " -> " + s.getName());
//            System.out.println("Has registered: " + s.getCourseRegistrations().size() + " courses.");
//
//            for (CourseRegistration registration : s.getCourseRegistrations()) {
//                System.out.println(registration);
//            }
//        }
    }

}
