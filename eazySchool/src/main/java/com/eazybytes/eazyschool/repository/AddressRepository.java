package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
}
