package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Convention;

import com.mycompany.myapp.repository.ConventionRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Convention.
 */
@RestController
@RequestMapping("/api")
public class ConventionResource {

    private final Logger log = LoggerFactory.getLogger(ConventionResource.class);
        
    @Inject
    private ConventionRepository conventionRepository;

    /**
     * POST  /conventions : Create a new convention.
     *
     * @param convention the convention to create
     * @return the ResponseEntity with status 201 (Created) and with body the new convention, or with status 400 (Bad Request) if the convention has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/conventions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Convention> createConvention(@RequestBody Convention convention) throws URISyntaxException {
        log.debug("REST request to save Convention : {}", convention);
        if (convention.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("convention", "idexists", "A new convention cannot already have an ID")).body(null);
        }
        Convention result = conventionRepository.save(convention);
        return ResponseEntity.created(new URI("/api/conventions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("convention", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conventions : Updates an existing convention.
     *
     * @param convention the convention to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated convention,
     * or with status 400 (Bad Request) if the convention is not valid,
     * or with status 500 (Internal Server Error) if the convention couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/conventions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Convention> updateConvention(@RequestBody Convention convention) throws URISyntaxException {
        log.debug("REST request to update Convention : {}", convention);
        if (convention.getId() == null) {
            return createConvention(convention);
        }
        Convention result = conventionRepository.save(convention);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("convention", convention.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conventions : get all the conventions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conventions in body
     */
    @RequestMapping(value = "/conventions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Convention> getAllConventions() {
        log.debug("REST request to get all Conventions");
        List<Convention> conventions = conventionRepository.findAll();
        return conventions;
    }

    /**
     * GET  /conventions/:id : get the "id" convention.
     *
     * @param id the id of the convention to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the convention, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/conventions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Convention> getConvention(@PathVariable Long id) {
        log.debug("REST request to get Convention : {}", id);
        Convention convention = conventionRepository.findOne(id);
        return Optional.ofNullable(convention)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /conventions/:id : delete the "id" convention.
     *
     * @param id the id of the convention to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/conventions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConvention(@PathVariable Long id) {
        log.debug("REST request to delete Convention : {}", id);
        conventionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("convention", id.toString())).build();
    }

}
