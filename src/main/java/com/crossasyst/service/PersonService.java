package com.crossasyst.service;

import com.crossasyst.entity.AddressEntity;
import com.crossasyst.entity.PersonEntity;
import com.crossasyst.mapper.AddressMapper;
import com.crossasyst.mapper.PersonMapper;
import com.crossasyst.model.PersonRequest;
import com.crossasyst.model.PersonResponse;
import com.crossasyst.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final AddressMapper addressMapper;

    @Autowired
    private PersonService(
            PersonRepository personRepository,
            PersonRepository personRepository1, PersonMapper personMapper, AddressMapper addressMapper
    ) {
        this.addressMapper = addressMapper;
        this.personRepository = personRepository1;
        this.personMapper = personMapper;

    }

    public PersonRequest getPersons(Long personId) {
        Optional<PersonEntity> person = personRepository.findById(personId);
        PersonRequest personRequest = personMapper.toModel(person.get());
        log.info("here is the persons ...........................");
        return personRequest;
    }

    public List<PersonRequest> getAllPersons() {
        List<PersonEntity> personEntityList = personRepository.findAll();
        List<PersonRequest> personRequestList = personMapper.toModels(personEntityList);
        log.info("here is the all persons ...........................");
        return personRequestList;
    }

    public PersonResponse createPerson(PersonRequest personRequest) {
        PersonEntity personEntity = personMapper.toEntity(personRequest);
        personEntity.getAddress().setPerson(personEntity);
        personRepository.save(personEntity);
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(personEntity.getId());
        log.info("The person is created into the database .......");
        return personResponse;
    }


    public PersonRequest updatePerson(Long personId, PersonRequest personRequest) {

        PersonEntity personEntity = personRepository.findById(personId).get();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress(personRequest.getAddress().getAddress());
        addressEntity.setState(personRequest.getAddress().getState());
        addressEntity.setCity(personRequest.getAddress().getCity());
        addressEntity.setZipcode(personRequest.getAddress().getZipcode());
        addressEntity.setId(personEntity.getAddress().getId());
        personEntity.setFirstName(personRequest.getFirstName());
        personEntity.setLastName(personRequest.getLastName());
        personEntity.setAddress(addressEntity);
        personRepository.save(personEntity);
        log.info("The data is successfully updated ......................" + personRequest);
        return personRequest;
    }

    public void deletePerson(Long personId) {
        personRepository.deleteById(personId);
    }
}

