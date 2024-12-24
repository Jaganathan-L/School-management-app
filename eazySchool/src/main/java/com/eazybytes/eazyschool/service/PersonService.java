package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.Roles;
import com.eazybytes.eazyschool.repository.AddressRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import com.eazybytes.eazyschool.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.eazybytes.eazyschool.constants.EazySchoolConstants.STUDENT_ROLE;

@Service
public class PersonService {

    private final RolesRepository rolesRepository;
    private final PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, RolesRepository rolesRepository ){
        this.personRepository = personRepository;
        this.rolesRepository =rolesRepository;
    }

    public boolean createNewUser(Person person){
        Roles roles =  rolesRepository.getByRoleName(STUDENT_ROLE);
        person.setRoles(roles);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        Person result = personRepository.save(person);
        return result.getPersonId() > 0;
    }
}
