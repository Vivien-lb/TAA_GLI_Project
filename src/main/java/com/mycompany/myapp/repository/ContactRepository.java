package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Contact;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
public interface ContactRepository extends JpaRepository<Contact,Long> {

    @Query("select distinct contact from Contact contact left join fetch contact.teachers")
    List<Contact> findAllWithEagerRelationships();

    @Query("select contact from Contact contact left join fetch contact.teachers where contact.id =:id")
    Contact findOneWithEagerRelationships(@Param("id") Long id);

}
