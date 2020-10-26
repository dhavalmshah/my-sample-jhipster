package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.CounterParty;
import com.dhavalapp.com.domain.Location;
import com.dhavalapp.com.repository.CounterPartyRepository;
import com.dhavalapp.com.service.CounterPartyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dhavalapp.com.domain.enumeration.CounterPartyType;
/**
 * Integration tests for the {@link CounterPartyResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CounterPartyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CounterPartyType DEFAULT_TYPE = CounterPartyType.CUSTOMER;
    private static final CounterPartyType UPDATED_TYPE = CounterPartyType.SELLER;

    private static final String DEFAULT_NOTES = "Muwlywu";
    private static final String UPDATED_NOTES = "Fvyipko";

    @Autowired
    private CounterPartyRepository counterPartyRepository;

    @Autowired
    private CounterPartyService counterPartyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCounterPartyMockMvc;

    private CounterParty counterParty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CounterParty createEntity(EntityManager em) {
        CounterParty counterParty = new CounterParty()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .notes(DEFAULT_NOTES);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        counterParty.getLocations().add(location);
        return counterParty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CounterParty createUpdatedEntity(EntityManager em) {
        CounterParty counterParty = new CounterParty()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .notes(UPDATED_NOTES);
        // Add required entity
        Location location;
        if (TestUtil.findAll(em, Location.class).isEmpty()) {
            location = LocationResourceIT.createUpdatedEntity(em);
            em.persist(location);
            em.flush();
        } else {
            location = TestUtil.findAll(em, Location.class).get(0);
        }
        counterParty.getLocations().add(location);
        return counterParty;
    }

    @BeforeEach
    public void initTest() {
        counterParty = createEntity(em);
    }

    @Test
    @Transactional
    public void createCounterParty() throws Exception {
        int databaseSizeBeforeCreate = counterPartyRepository.findAll().size();
        // Create the CounterParty
        restCounterPartyMockMvc.perform(post("/api/counter-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterParty)))
            .andExpect(status().isCreated());

        // Validate the CounterParty in the database
        List<CounterParty> counterPartyList = counterPartyRepository.findAll();
        assertThat(counterPartyList).hasSize(databaseSizeBeforeCreate + 1);
        CounterParty testCounterParty = counterPartyList.get(counterPartyList.size() - 1);
        assertThat(testCounterParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCounterParty.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCounterParty.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createCounterPartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = counterPartyRepository.findAll().size();

        // Create the CounterParty with an existing ID
        counterParty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCounterPartyMockMvc.perform(post("/api/counter-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterParty)))
            .andExpect(status().isBadRequest());

        // Validate the CounterParty in the database
        List<CounterParty> counterPartyList = counterPartyRepository.findAll();
        assertThat(counterPartyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = counterPartyRepository.findAll().size();
        // set the field null
        counterParty.setName(null);

        // Create the CounterParty, which fails.


        restCounterPartyMockMvc.perform(post("/api/counter-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterParty)))
            .andExpect(status().isBadRequest());

        List<CounterParty> counterPartyList = counterPartyRepository.findAll();
        assertThat(counterPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCounterParties() throws Exception {
        // Initialize the database
        counterPartyRepository.saveAndFlush(counterParty);

        // Get all the counterPartyList
        restCounterPartyMockMvc.perform(get("/api/counter-parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(counterParty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }
    
    @Test
    @Transactional
    public void getCounterParty() throws Exception {
        // Initialize the database
        counterPartyRepository.saveAndFlush(counterParty);

        // Get the counterParty
        restCounterPartyMockMvc.perform(get("/api/counter-parties/{id}", counterParty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(counterParty.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }
    @Test
    @Transactional
    public void getNonExistingCounterParty() throws Exception {
        // Get the counterParty
        restCounterPartyMockMvc.perform(get("/api/counter-parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCounterParty() throws Exception {
        // Initialize the database
        counterPartyService.save(counterParty);

        int databaseSizeBeforeUpdate = counterPartyRepository.findAll().size();

        // Update the counterParty
        CounterParty updatedCounterParty = counterPartyRepository.findById(counterParty.getId()).get();
        // Disconnect from session so that the updates on updatedCounterParty are not directly saved in db
        em.detach(updatedCounterParty);
        updatedCounterParty
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .notes(UPDATED_NOTES);

        restCounterPartyMockMvc.perform(put("/api/counter-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCounterParty)))
            .andExpect(status().isOk());

        // Validate the CounterParty in the database
        List<CounterParty> counterPartyList = counterPartyRepository.findAll();
        assertThat(counterPartyList).hasSize(databaseSizeBeforeUpdate);
        CounterParty testCounterParty = counterPartyList.get(counterPartyList.size() - 1);
        assertThat(testCounterParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCounterParty.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCounterParty.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingCounterParty() throws Exception {
        int databaseSizeBeforeUpdate = counterPartyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCounterPartyMockMvc.perform(put("/api/counter-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(counterParty)))
            .andExpect(status().isBadRequest());

        // Validate the CounterParty in the database
        List<CounterParty> counterPartyList = counterPartyRepository.findAll();
        assertThat(counterPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCounterParty() throws Exception {
        // Initialize the database
        counterPartyService.save(counterParty);

        int databaseSizeBeforeDelete = counterPartyRepository.findAll().size();

        // Delete the counterParty
        restCounterPartyMockMvc.perform(delete("/api/counter-parties/{id}", counterParty.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CounterParty> counterPartyList = counterPartyRepository.findAll();
        assertThat(counterPartyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
