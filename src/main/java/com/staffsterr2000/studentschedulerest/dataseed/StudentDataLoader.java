package com.staffsterr2000.studentschedulerest.dataseed;

import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StudentDataLoader implements CommandLineRunner {

    private final StudentService studentService;

    @Override
    public void run(String... args) throws Exception {
        loadStudentData();
    }

    public void loadStudentData() {
        Student student = new Student();
        student.setFirstName("Stan");
        student.setLastName("Rock");
        studentService.addStudent(student);
    }

}
