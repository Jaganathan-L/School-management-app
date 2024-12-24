package com.eazybytes.eazyschool.rest;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.model.Response;
import com.eazybytes.eazyschool.repository.ContactRepositoryJPA;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path="/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class ContactRestController {

    @Autowired
    private ContactRepositoryJPA repository;

    @GetMapping("/getMessagesByStatus")
    //@ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status") String status){
        return repository.readByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
   // @ResponseBody
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact){
        if(null != contact && null != contact.getStatus()){
            return repository.readByStatus(contact.getStatus());
        }else {
            return List.of();
        }
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocation, @Valid @RequestBody Contact contact){

        log.info("Invocation From : " + invocation);
        Contact resultContact =repository.save(contact);
        Response response = new Response();
        boolean isCreated = false;
        if (null != resultContact && resultContact.getContactId() > 0){
            isCreated = true;
            response.setStatusCode(HttpStatus.CREATED.toString());
            response.setStatusMessage("Contact Message created successful");
        }else {
            response.setStatusCode(HttpStatus.BAD_REQUEST.toString());
            response.setStatusMessage("Contact Message Not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("contactSaved", String.valueOf(isCreated))
                .body(response);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))
            ));
        });

        Contact contact = requestEntity.getBody();
        repository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.toString());
        response.setStatusMessage("Message deleted successful");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Contact Delected", String.valueOf(true))
                .body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contact){
        Response response = new Response();
        Optional<Contact> contactFromDb = repository.findById(contact.getContactId());
        if (contactFromDb.isPresent()){
            contactFromDb.get().setStatus(EazySchoolConstants.CLOSE);
            repository.save(contactFromDb.get());
        }else {
            response.setStatusCode(HttpStatus.BAD_REQUEST.toString());
            response.setStatusMessage("Invalid request input ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusMessage("Close message Updated ");
        response.setStatusCode(HttpStatus.OK.toString());
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
