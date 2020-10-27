package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.TransportDetails;
import com.dhavalapp.com.repository.TransportDetailsRepository;
import com.dhavalapp.com.service.TransportDetailsService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransportDetailsResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransportDetailsResourceIT {

    private static final String DEFAULT_TRANSPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPORT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_TRANSPORT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSPORT_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTUAL_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTUAL_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TransportDetailsRepository transportDetailsRepository;

    @Autowired
    private TransportDetailsService transportDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransportDetailsMockMvc;

    private TransportDetails transportDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransportDetails createEntity(EntityManager em) {
        TransportDetails transportDetails = new TransportDetails()
            .transportNumber(DEFAULT_TRANSPORT_NUMBER)
            .transportStartDate(DEFAULT_TRANSPORT_START_DATE)
            .estimatedEndDate(DEFAULT_ESTIMATED_END_DATE)
            .actualEndDate(DEFAULT_ACTUAL_END_DATE);
        return transportDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransportDetails createUpdatedEntity(EntityManager em) {
        TransportDetails transportDetails = new TransportDetails()
            .transportNumber(UPDATED_TRANSPORT_NUMBER)
            .transportStartDate(UPDATED_TRANSPORT_START_DATE)
            .estimatedEndDate(UPDATED_ESTIMATED_END_DATE)
            .actualEndDate(UPDATED_ACTUAL_END_DATE);
        return transportDetails;
    }

    @BeforeEach
    public void initTest() {
        transportDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransportDetails() throws Exception {
        int databaseSizeBeforeCreate = transportDetailsRepository.findAll().size();
        // Create the TransportDetails
        restTransportDetailsMockMvc.perform(post("/api/transport-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportDetails)))
            .andExpect(status().isCreated());

        // Validate the TransportDetails in the database
        List<TransportDetails> transportDetailsList = transportDetailsRepository.findAll();
        assertThat(transportDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        TransportDetails testTransportDetails = transportDetailsList.get(transportDetailsList.size() - 1);
        assertThat(testTransportDetails.getTransportNumber()).isEqualTo(DEFAULT_TRANSPORT_NUMBER);
        assertThat(testTransportDetails.getTransportStartDate()).isEqualTo(DEFAULT_TRANSPORT_START_DATE);
        assertThat(testTransportDetails.getEstimatedEndDate()).isEqualTo(DEFAULT_ESTIMATED_END_DATE);
        assertThat(testTransportDetails.getActualEndDate()).isEqualTo(DEFAULT_ACTUAL_END_DATE);
    }

    @Test
    @Transactional
    public void createTransportDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportDetailsRepository.findAll().size();

        // Create the TransportDetails with an existing ID
        transportDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportDetailsMockMvc.perform(post("/api/transport-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportDetails)))
            .andExpect(status().isBadRequest());

        // Validate the TransportDetails in the database
        List<TransportDetails> transportDetailsList = transportDetailsRepository.findAll();
        assertThat(transportDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransportNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportDetailsRepository.findAll().size();
        // set the field null
        transportDetails.setTransportNumber(null);

        // Create the TransportDetails, which fails.


        restTransportDetailsMockMvc.perform(post("/api/transport-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportDetails)))
            .andExpect(status().isBadRequest());

        List<TransportDetails> transportDetailsList = transportDetailsRepository.findAll();
        assertThat(transportDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransportDetails() throws Exception {
        // Initialize the database
        transportDetailsRepository.saveAndFlush(transportDetails);

        // Get all the transportDetailsList
        restTransportDetailsMockMvc.perform(get("/api/transport-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transportDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].transportNumber").value(hasItem(DEFAULT_TRANSPORT_NUMBER)))
            .andExpect(jsonPath("$.[*].transportStartDate").value(hasItem(DEFAULT_TRANSPORT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedEndDate").value(hasItem(DEFAULT_ESTIMATED_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].actualEndDate").value(hasItem(DEFAULT_ACTUAL_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTransportDetails() throws Exception {
        // Initialize the database
        transportDetailsRepository.saveAndFlush(transportDetails);

        // Get the transportDetails
        restTransportDetailsMockMvc.perform(get("/api/transport-details/{id}", transportDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transportDetails.getId().intValue()))
            .andExpect(jsonPath("$.transportNumber").value(DEFAULT_TRANSPORT_NUMBER))
            .andExpect(jsonPath("$.transportStartDate").value(DEFAULT_TRANSPORT_START_DATE.toString()))
            .andExpect(jsonPath("$.estimatedEndDate").value(DEFAULT_ESTIMATED_END_DATE.toString()))
            .andExpect(jsonPath("$.actualEndDate").value(DEFAULT_ACTUAL_END_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTransportDetails() throws Exception {
        // Get the transportDetails
        restTransportDetailsMockMvc.perform(get("/api/transport-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransportDetails() throws Exception {
        // Initialize the database
        transportDetailsService.save(transportDetails);

        int databaseSizeBeforeUpdate = transportDetailsRepository.findAll().size();

        // Update the transportDetails
        TransportDetails updatedTransportDetails = transportDetailsRepository.findById(transportDetails.getId()).get();
        // Disconnect from session so that the updates on updatedTransportDetails are not directly saved in db
        em.detach(updatedTransportDetails);
        updatedTransportDetails
            .transportNumber(UPDATED_TRANSPORT_NUMBER)
            .transportStartDate(UPDATED_TRANSPORT_START_DATE)
            .estimatedEndDate(UPDATED_ESTIMATED_END_DATE)
            .actualEndDate(UPDATED_ACTUAL_END_DATE);

        restTransportDetailsMockMvc.perform(put("/api/transport-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransportDetails)))
            .andExpect(status().isOk());

        // Validate the TransportDetails in the database
        List<TransportDetails> transportDetailsList = transportDetailsRepository.findAll();
        assertThat(transportDetailsList).hasSize(databaseSizeBeforeUpdate);
        TransportDetails testTransportDetails = transportDetailsList.get(transportDetailsList.size() - 1);
        assertThat(testTransportDetails.getTransportNumber()).isEqualTo(UPDATED_TRANSPORT_NUMBER);
        assertThat(testTransportDetails.getTransportStartDate()).isEqualTo(UPDATED_TRANSPORT_START_DATE);
        assertThat(testTransportDetails.getEstimatedEndDate()).isEqualTo(UPDATED_ESTIMATED_END_DATE);
        assertThat(testTransportDetails.getActualEndDate()).isEqualTo(UPDATED_ACTUAL_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransportDetails() throws Exception {
        int databaseSizeBeforeUpdate = transportDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportDetailsMockMvc.perform(put("/api/transport-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportDetails)))
            .andExpect(status().isBadRequest());

        // Validate the TransportDetails in the database
        List<TransportDetails> transportDetailsList = transportDetailsRepository.findAll();
        assertThat(transportDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransportDetails() throws Exception {
        // Initialize the database
        transportDetailsService.save(transportDetails);

        int databaseSizeBeforeDelete = transportDetailsRepository.findAll().size();

        // Delete the transportDetails
        restTransportDetailsMockMvc.perform(delete("/api/transport-details/{id}", transportDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransportDetails> transportDetailsList = transportDetailsRepository.findAll();
        assertThat(transportDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
