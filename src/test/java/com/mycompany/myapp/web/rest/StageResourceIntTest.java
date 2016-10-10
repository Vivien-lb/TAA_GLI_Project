package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TryApp;

import com.mycompany.myapp.domain.Stage;
import com.mycompany.myapp.repository.StageRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StageResource REST controller.
 *
 * @see StageResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TryApp.class)

public class StageResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);
    private static final String DEFAULT_ADRESS = "AAAAA";
    private static final String UPDATED_ADRESS = "BBBBB";

    private static final ZonedDateTime DEFAULT_SOON_ENDING = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SOON_ENDING = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SOON_ENDING_STR = dateTimeFormatter.format(DEFAULT_SOON_ENDING);

    private static final Integer DEFAULT_STUDENT = 1;
    private static final Integer UPDATED_STUDENT = 2;

    @Inject
    private StageRepository stageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStageMockMvc;

    private Stage stage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StageResource stageResource = new StageResource();
        ReflectionTestUtils.setField(stageResource, "stageRepository", stageRepository);
        this.restStageMockMvc = MockMvcBuilders.standaloneSetup(stageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createEntity(EntityManager em) {
        Stage stage = new Stage()
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .adress(DEFAULT_ADRESS)
                .soonEnding(DEFAULT_SOON_ENDING)
                .student(DEFAULT_STUDENT);
        return stage;
    }

    @Before
    public void initTest() {
        stage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStage.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testStage.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testStage.getSoonEnding()).isEqualTo(DEFAULT_SOON_ENDING);
        assertThat(testStage.getStudent()).isEqualTo(DEFAULT_STUDENT);
    }

    @Test
    @Transactional
    public void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stages
        restStageMockMvc.perform(get("/api/stages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())))
                .andExpect(jsonPath("$.[*].soonEnding").value(hasItem(DEFAULT_SOON_ENDING_STR)))
                .andExpect(jsonPath("$.[*].student").value(hasItem(DEFAULT_STUDENT)));
    }

    @Test
    @Transactional
    public void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()))
            .andExpect(jsonPath("$.soonEnding").value(DEFAULT_SOON_ENDING_STR))
            .andExpect(jsonPath("$.student").value(DEFAULT_STUDENT));
    }

    @Test
    @Transactional
    public void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        Stage updatedStage = stageRepository.findOne(stage.getId());
        updatedStage
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .adress(UPDATED_ADRESS)
                .soonEnding(UPDATED_SOON_ENDING)
                .student(UPDATED_STUDENT);

        restStageMockMvc.perform(put("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStage)))
                .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStage.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testStage.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testStage.getSoonEnding()).isEqualTo(UPDATED_SOON_ENDING);
        assertThat(testStage.getStudent()).isEqualTo(UPDATED_STUDENT);
    }

    @Test
    @Transactional
    public void deleteStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Get the stage
        restStageMockMvc.perform(delete("/api/stages/{id}", stage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
