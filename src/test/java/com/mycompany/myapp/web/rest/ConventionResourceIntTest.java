package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TryApp;

import com.mycompany.myapp.domain.Convention;
import com.mycompany.myapp.repository.ConventionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConventionResource REST controller.
 *
 * @see ConventionResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TryApp.class)

public class ConventionResourceIntTest {
    private static final String DEFAULT_SUJET = "AAAAA";
    private static final String UPDATED_SUJET = "BBBBB";

    private static final Boolean DEFAULT_STUDENT_SIGNATURE = false;
    private static final Boolean UPDATED_STUDENT_SIGNATURE = true;

    private static final Boolean DEFAULT_CONTACT_SIGNATURE = false;
    private static final Boolean UPDATED_CONTACT_SIGNATURE = true;

    private static final Boolean DEFAULT_UNIVERSITY_SIGNATURE = false;
    private static final Boolean UPDATED_UNIVERSITY_SIGNATURE = true;

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;

    @Inject
    private ConventionRepository conventionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConventionMockMvc;

    private Convention convention;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConventionResource conventionResource = new ConventionResource();
        ReflectionTestUtils.setField(conventionResource, "conventionRepository", conventionRepository);
        this.restConventionMockMvc = MockMvcBuilders.standaloneSetup(conventionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convention createEntity(EntityManager em) {
        Convention convention = new Convention()
                .sujet(DEFAULT_SUJET)
                .studentSignature(DEFAULT_STUDENT_SIGNATURE)
                .contactSignature(DEFAULT_CONTACT_SIGNATURE)
                .universitySignature(DEFAULT_UNIVERSITY_SIGNATURE)
                .salary(DEFAULT_SALARY);
        return convention;
    }

    @Before
    public void initTest() {
        convention = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvention() throws Exception {
        int databaseSizeBeforeCreate = conventionRepository.findAll().size();

        // Create the Convention

        restConventionMockMvc.perform(post("/api/conventions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(convention)))
                .andExpect(status().isCreated());

        // Validate the Convention in the database
        List<Convention> conventions = conventionRepository.findAll();
        assertThat(conventions).hasSize(databaseSizeBeforeCreate + 1);
        Convention testConvention = conventions.get(conventions.size() - 1);
        assertThat(testConvention.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testConvention.isStudentSignature()).isEqualTo(DEFAULT_STUDENT_SIGNATURE);
        assertThat(testConvention.isContactSignature()).isEqualTo(DEFAULT_CONTACT_SIGNATURE);
        assertThat(testConvention.isUniversitySignature()).isEqualTo(DEFAULT_UNIVERSITY_SIGNATURE);
        assertThat(testConvention.getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    @Transactional
    public void getAllConventions() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get all the conventions
        restConventionMockMvc.perform(get("/api/conventions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(convention.getId().intValue())))
                .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
                .andExpect(jsonPath("$.[*].studentSignature").value(hasItem(DEFAULT_STUDENT_SIGNATURE.booleanValue())))
                .andExpect(jsonPath("$.[*].contactSignature").value(hasItem(DEFAULT_CONTACT_SIGNATURE.booleanValue())))
                .andExpect(jsonPath("$.[*].universitySignature").value(hasItem(DEFAULT_UNIVERSITY_SIGNATURE.booleanValue())))
                .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())));
    }

    @Test
    @Transactional
    public void getConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);

        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", convention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(convention.getId().intValue()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.studentSignature").value(DEFAULT_STUDENT_SIGNATURE.booleanValue()))
            .andExpect(jsonPath("$.contactSignature").value(DEFAULT_CONTACT_SIGNATURE.booleanValue()))
            .andExpect(jsonPath("$.universitySignature").value(DEFAULT_UNIVERSITY_SIGNATURE.booleanValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConvention() throws Exception {
        // Get the convention
        restConventionMockMvc.perform(get("/api/conventions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);
        int databaseSizeBeforeUpdate = conventionRepository.findAll().size();

        // Update the convention
        Convention updatedConvention = conventionRepository.findOne(convention.getId());
        updatedConvention
                .sujet(UPDATED_SUJET)
                .studentSignature(UPDATED_STUDENT_SIGNATURE)
                .contactSignature(UPDATED_CONTACT_SIGNATURE)
                .universitySignature(UPDATED_UNIVERSITY_SIGNATURE)
                .salary(UPDATED_SALARY);

        restConventionMockMvc.perform(put("/api/conventions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedConvention)))
                .andExpect(status().isOk());

        // Validate the Convention in the database
        List<Convention> conventions = conventionRepository.findAll();
        assertThat(conventions).hasSize(databaseSizeBeforeUpdate);
        Convention testConvention = conventions.get(conventions.size() - 1);
        assertThat(testConvention.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testConvention.isStudentSignature()).isEqualTo(UPDATED_STUDENT_SIGNATURE);
        assertThat(testConvention.isContactSignature()).isEqualTo(UPDATED_CONTACT_SIGNATURE);
        assertThat(testConvention.isUniversitySignature()).isEqualTo(UPDATED_UNIVERSITY_SIGNATURE);
        assertThat(testConvention.getSalary()).isEqualTo(UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void deleteConvention() throws Exception {
        // Initialize the database
        conventionRepository.saveAndFlush(convention);
        int databaseSizeBeforeDelete = conventionRepository.findAll().size();

        // Get the convention
        restConventionMockMvc.perform(delete("/api/conventions/{id}", convention.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Convention> conventions = conventionRepository.findAll();
        assertThat(conventions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
