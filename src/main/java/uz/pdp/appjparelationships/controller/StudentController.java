package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Student;
import uz.pdp.appjparelationships.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    //VAZIRLIK UCHUN
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //UNIVERSITET UCHUN
    @GetMapping("/forUnivercity/{univercityId}")
    public Page<Student> getStudentListForUnivercity(@PathVariable Integer univercityId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UnivercityId(univercityId, pageable);
        return studentPage;
    }

    //FACULTY ID ORQALI KORISH
    @GetMapping(value = "/byFacultyId/{id}")
    public List<Student> getStudentsByFacultyId(@PathVariable Integer id) {
        return studentRepository.getStudentsByFacultyId(id);
    }

    //GROUP RAHBARI ID ORQALI O'Z GURUHIDAGILARNI KO'ROLADI
    @GetMapping(value = "/byGroupId/{id}")
    public List<Student> getStudentByGroupId(@PathVariable Integer id) {
        return studentRepository.getStudentsByGroupId(id);
    }
}
