package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.annotation.FieldsValueMatch;
import com.eazybytes.eazyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "course")
public interface CoursesRepository extends JpaRepository<Courses,Integer> {

    List<Courses> findByOrderByNameDesc();

    List<Courses> findByOrderByName();
}
