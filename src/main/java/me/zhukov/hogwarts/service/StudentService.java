package me.zhukov.hogwarts.service;

import me.zhukov.hogwarts.model.Faculty;
import me.zhukov.hogwarts.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student removeStudent(long id);

    Student findStudent(long id);

    void updateStudent(long id, Student studentForUpdate);

    List<Student> findAllByAge(int age);

    List<Student> findByAgeBetween(int ageMin, int ageMax);
}
