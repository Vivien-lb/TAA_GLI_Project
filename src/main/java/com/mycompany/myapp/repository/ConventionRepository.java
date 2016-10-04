package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Convention;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Convention entity.
 */
@SuppressWarnings("unused")
public interface ConventionRepository extends JpaRepository<Convention,Long> {

}
