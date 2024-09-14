package me.zhukov.hogwarts.controller;

import me.zhukov.hogwarts.model.Student;
import me.zhukov.hogwarts.service.impl.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}/get")
    public Student findStudent(@PathVariable("id") long id) {
        return studentService.findStudent(id);
    }

    @DeleteMapping("/{id}/remove")
    public Student removeStudent(@PathVariable("id") long id) {
        return studentService.removeStudent(id);
    }

    @PutMapping("/{id}/update")
    public void updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        studentService.updateStudent(id, student);
    }

    @GetMapping("/get/by-age")
    public List<Student> findAllByAge(@RequestParam("age") int age) {
        return studentService.findAllByAge(age);
    }

    @GetMapping("/get/by-age-between")
    public List<Student> findByAgeBetween(int ageMin, int ageMax) {
        return studentService.findByAgeBetween(ageMin, ageMax);
    }
}
