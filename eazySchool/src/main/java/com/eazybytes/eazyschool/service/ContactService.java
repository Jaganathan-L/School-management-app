package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.cofig.EazySchoolProps;
import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import com.eazybytes.eazyschool.repository.ContactRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ContactService {

    private final ContactRepositoryJPA contactRepository;

    @Autowired
    private EazySchoolProps eazySchoolProps;


    public ContactService(ContactRepositoryJPA contactRepository){
        this.contactRepository = contactRepository;
        System.out.println("Contact Service bean created.");
    }

    public boolean saveMessageDetails(Contact contact){
        boolean isUpdated = false;
        contact.setStatus(EazySchoolConstants.OPEN);
        Contact result = contactRepository.save(contact);
        if(result != null && result.getContactId() > 0){
            isUpdated = true;
        }
        return isUpdated;
    }

    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> contactMsgs = contactRepository.readByStatus(EazySchoolConstants.OPEN);
        return contactMsgs;
    }

    public Page<Contact> findMsgsWithOpenStatusAndPagination(int pageNum, String sortName, String sortDir){
        int pageSize = eazySchoolProps.getPageSize();
        if(null != eazySchoolProps.getContact() && null != eazySchoolProps.getContact().get("pageSize")){
            pageSize = Integer.parseInt(eazySchoolProps.getContact().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNum-1,pageSize,
                sortDir.equals("asc")? Sort.by(sortName).ascending(): Sort.by(sortName).descending());
        Page<Contact> contactsPage = contactRepository.findByStatusWithQuery(EazySchoolConstants.OPEN,pageable);
        return contactsPage;
    }

    public boolean updateCloseMsg(int id){
        boolean isCloseMsgUpdated = false;
        int result = contactRepository.updateStatusById(EazySchoolConstants.CLOSE,id);
        if(result > 0){
            isCloseMsgUpdated = true;
        }
        return isCloseMsgUpdated;
    }

}














































































































































































































































