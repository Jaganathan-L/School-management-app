package com.eazybytes.eazyschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int addressId;

    @NotNull(message = "Address should not be empty")
    @Size(min = 5,message = "Address value should be greater than 5 character")
    private String address1;

    private String address2;

    @NotNull(message = "City should not be empty")
    @Size(min=3, message = "City value should be greater than 3 character")
    private String city;

    @NotNull(message = "State should not be empty")
    @Size(min=3, message = "State value should be greater than 3 character")
    private String state;

    @NotNull(message = "Zip Code should not be empty")
    @Pattern(regexp="(^$|[0-9]{5})",message = "Zip Code must be 5 digits")
    private String zipCode;
}
