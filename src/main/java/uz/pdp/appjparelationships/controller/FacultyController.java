package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.entity.Faculty;
import uz.pdp.appjparelationships.entity.Univercity;
import uz.pdp.appjparelationships.payload.FacultyDto;
import uz.pdp.appjparelationships.repository.FacultyRepository;
import uz.pdp.appjparelationships.repository.UnivercityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UnivercityRepository univercityRepository;

    @GetMapping
    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {
        boolean exists = facultyRepository.existsByNameAndUnivercityId(facultyDto.getName(), facultyDto.getUnivercityId());
        if (exists) {
            return "This faculty already exist";
        }
        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        Optional<Univercity> optionalUnivercity = univercityRepository.findById(facultyDto.getUnivercityId());
        if (!optionalUnivercity.isPresent()) {
            return "Univercity not found";
        }
        faculty.setUnivercity(optionalUnivercity.get());


        facultyRepository.save(faculty);
        return "Faculty saved";
    }

    @GetMapping(value = "/byUnivercityId/{id}")
    public List<Faculty> getFacultiesByUnivercityId(@PathVariable Integer id) {
        List<Faculty> allByUnivercityId = facultyRepository.findAllByUnivercityId(id);
        return allByUnivercityId;
    }

    @GetMapping(value = "/byFacultyId/{id}")
    public Faculty getFacultyById(@PathVariable Integer id) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        if (!facultyOptional.isPresent()) {
            return new Faculty();
        }
        return facultyOptional.get();
    }

    @PutMapping(value = "/updateFaculty/{id}")
    public String updateFacultyById(@PathVariable Integer id, @RequestBody FacultyDto facultyDto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty editingFaculty = optionalFaculty.get();
            editingFaculty.setName(facultyDto.getName());
            Optional<Univercity> optionalUnivercity = univercityRepository.findById(facultyDto.getUnivercityId());
            if (!optionalUnivercity.isPresent()) {
                return "University not found";
            }
            editingFaculty.setUnivercity(optionalUnivercity.get());
            boolean exists=facultyRepository.existsByNameAndUnivercityId(editingFaculty.getName(), editingFaculty.getUnivercity().getId());
            if (exists){
                return "this faculty already exists";
            }
            facultyRepository.save(editingFaculty);
            return "Faculty edited";
        } else {
            return "Faculty not found";
        }
    }

    @DeleteMapping(value = "/deleteFaculty/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        try {
            facultyRepository.deleteById(id);
            return "Faculty deleted!";
        } catch (Exception e) {
            return "Error in deleting";
        }
    }
}
