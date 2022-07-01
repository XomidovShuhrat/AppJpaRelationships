package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Faculty;
import uz.pdp.appjparelationships.entity.Group;
import uz.pdp.appjparelationships.entity.Univercity;
import uz.pdp.appjparelationships.payload.GroupDto;
import uz.pdp.appjparelationships.repository.FacultyRepository;
import uz.pdp.appjparelationships.repository.RepositoryGroup;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    RepositoryGroup groupRepository;
    @Autowired
    FacultyRepository facultyRepository;

//VAZIRLIK UCHUN READ
    @GetMapping
    public List<Group> getGroups(){
        return groupRepository.findAll();
    }

//UNIVERSITET MASULLARI UCHUN
    @GetMapping(value = "/byUnivercityId/{univercityId}")
    public List<Group> getGroupsByUnivercityId(@PathVariable Integer univercityId){
        List<Group> groupsByUnivercityId = groupRepository.getGroupsByUnivercityId(univercityId);
        List<Group> groupsByUnivercityIdNative = groupRepository.getGroupsByUnivercityIdNative(univercityId);
        return groupRepository.findAllByFaculty_UnivercityId(univercityId);

    }

    @PostMapping
    public String postGroup(@RequestBody GroupDto groupDto){
        boolean exists= groupRepository.existsByNameAndFaculty_Id(groupDto.getName(),groupDto.getFacultyId());
        if (exists){
            return "This group already exists";
        }
        Group group=new Group();
        group.setName(groupDto.getName());
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (!optionalFaculty.isPresent()){
            return "Faculty not found! ";
        }
        Faculty faculty = optionalFaculty.get();
        group.setFaculty(faculty);
        groupRepository.save(group);
        return "Group added";
    }


    @PutMapping(value = "/{id}")
    public String updateGroup(@PathVariable Integer id, @RequestBody GroupDto groupDto){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if(!optionalGroup.isPresent()){
            return "This group is not exist";
        }else{
            Group editingGroup = optionalGroup.get();
            editingGroup.setName(groupDto.getName());
            Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
            if (!optionalFaculty.isPresent()){
                return "This faculty not exist";
            }else {
                editingGroup.setFaculty(optionalFaculty.get());
                boolean exists=groupRepository.existsByNameAndFaculty_Id(editingGroup.getName(),editingGroup.getFaculty().getId());
                if (exists){
                    return " this group already exists";
                }
                groupRepository.save(editingGroup);
                return "Group edited";
            }
        }
    }


    @DeleteMapping(value = "/{id}")
    public String deleteGroup(@PathVariable Integer id){
        groupRepository.deleteById(id);
        return "Group deleted";
    }
}
