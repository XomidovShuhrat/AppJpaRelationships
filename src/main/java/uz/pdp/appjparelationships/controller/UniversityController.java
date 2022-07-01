package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.entity.Univercity;
import uz.pdp.appjparelationships.payload.UnivercityDto;
import uz.pdp.appjparelationships.repository.AddressRepository;
import uz.pdp.appjparelationships.repository.UnivercityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UnivercityRepository univercityRepository;
    @Autowired
    AddressRepository addressRepository;

    //READ
    @RequestMapping(value = "/university",method = RequestMethod.GET)
    public List<Univercity> getUnivercities(){
        return univercityRepository.findAll();
    }
    
    //READ BY ID
    @RequestMapping(value = "/univercity/{id}",method = RequestMethod.GET)
    public Univercity getUnivercityById(@PathVariable Integer id){
        Optional<Univercity> byId = univercityRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }else {
            return new Univercity();
        }
    }

    //CREATE
    @RequestMapping(value = "/university",method = RequestMethod.POST)
    public String addUnivercity(@RequestBody UnivercityDto univercityDto){
        Address address=new Address();
        address.setCity(univercityDto.getCity());
        address.setDistrict(univercityDto.getDistrict());
        address.setStreet(univercityDto.getStreet());
        Address savedAddress = addressRepository.save(address);
        Univercity univercity=new Univercity();
        univercity.setName(univercityDto.getName());
        univercity.setAddress(savedAddress);
        univercityRepository.save(univercity);

        return "Univercity added!";
    }

    //DELETE
    @RequestMapping(value = "/univercity/{id}",method = RequestMethod.DELETE)
    public String deleteUnivercity(@PathVariable Integer id){
        univercityRepository.deleteById(id);
        return "Univercity has deleted";
    }

    //UPDATE
    @RequestMapping(value = "/univercity/{id}",method = RequestMethod.PUT)
    public String updateUnivercity(@PathVariable Integer id,@RequestBody UnivercityDto univercityDto){
        Optional<Univercity> optionalUnivercity = univercityRepository.findById(id);
        if (optionalUnivercity.isPresent()){
            Univercity editingUnivercity = optionalUnivercity.get();
            editingUnivercity.setName(univercityDto.getName());
            Address address=new Address();
            address.setCity(univercityDto.getCity());
            address.setDistrict(univercityDto.getDistrict());
            address.setStreet(univercityDto.getStreet());
            editingUnivercity.setAddress(address);
            univercityRepository.save(editingUnivercity);
            return "Univercity edited";
        }else {
            return "Univercity not found";
        }
    }
}
