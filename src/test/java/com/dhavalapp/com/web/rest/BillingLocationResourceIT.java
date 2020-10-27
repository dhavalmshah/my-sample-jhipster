package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.BillingLocation;
import com.dhavalapp.com.domain.Contact;
import com.dhavalapp.com.repository.BillingLocationRepository;
import com.dhavalapp.com.service.BillingLocationService;

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

/**
 * Integration tests for the {@link BillingLocationResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BillingLocationResourceIT {

    private static final String DEFAULT_NAME = "Crr";
    private static final String UPDATED_NAME = "Yhi485";

    private static final String DEFAULT_SHORT_CODE = "Uhldz034";
    private static final String UPDATED_SHORT_CODE = "Ifaa7186";

    private static final String DEFAULT_PAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GST_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GST_NUMBER = "BBBBBBBBBB";

    @Autowired
    private BillingLocationRepository billingLocationRepository;

    @Autowired
    private BillingLocationService billingLocationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillingLocationMockMvc;

    private BillingLocation billingLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingLocation createEntity(EntityManager em) {
        BillingLocation billingLocation = new BillingLocation()
            .name(DEFAULT_NAME)
            .shortCode(DEFAULT_SHORT_CODE)
            .panNumber(DEFAULT_PAN_NUMBER)
            .gstNumber(DEFAULT_GST_NUMBER);
        // Add required entity
        Contact contact;
        if (TestUtil.findAll(em, Contact.class).isEmpty()) {
            contact = ContactResourceIT.createEntity(em);
            em.persist(contact);
            em.flush();
        } else {
            contact = TestUtil.findAll(em, Contact.class).get(0);
        }
        billingLocation.setContact(contact);
        return billingLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingLocation createUpdatedEntity(EntityManager em) {
        BillingLocation billingLocation = new BillingLocation()
            .name(UPDATED_NAME)
            .shortCode(UPDATED_SHORT_CODE)
            .panNumber(UPDATED_PAN_NUMBER)
            .gstNumber(UPDATED_GST_NUMBER);
        // Add required entity
        Contact contact;
        if (TestUtil.findAll(em, Contact.class).isEmpty()) {
            contact = ContactResourceIT.createUpdatedEntity(em);
            em.persist(contact);
            em.flush();
        } else {
            contact = TestUtil.findAll(em, Contact.class).get(0);
        }
        billingLocation.setContact(contact);
        return billingLocation;
    }

    @BeforeEach
    public void initTest() {
        billingLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingLocation() throws Exception {
        int databaseSizeBeforeCreate = billingLocationRepository.findAll().size();
        // Create the BillingLocation
        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingLocation)))
            .andExpect(status().isCreated());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeCreate + 1);
        BillingLocation testBillingLocation = billingLocationList.get(billingLocationList.size() - 1);
        assertThat(testBillingLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBillingLocation.getShortCode()).isEqualTo(DEFAULT_SHORT_CODE);
        assertThat(testBillingLocation.getPanNumber()).isEqualTo(DEFAULT_PAN_NUMBER);
        assertThat(testBillingLocation.getGstNumber()).isEqualTo(DEFAULT_GST_NUMBER);
    }

    @Test
    @Transactional
    public void createBillingLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingLocationRepository.findAll().size();

        // Create the BillingLocation with an existing ID
        billingLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingLocation)))
            .andExpect(status().isBadRequest());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingLocationRepository.findAll().size();
        // set the field null
        billingLocation.setName(null);

        // Create the BillingLocation, which fails.


        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingLocation)))
            .andExpect(status().isBadRequest());

        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingLocationRepository.findAll().size();
        // set the field null
        billingLocation.setShortCode(null);

        // Create the BillingLocation, which fails.


        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingLocation)))
            .andExpect(status().isBadRequest());

        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPanNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingLocationRepository.findAll().size();
        // set the field null
        billingLocation.setPanNumber(null);

        // Create the BillingLocation, which fails.


        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingLocation)))
            .andExpect(status().isBadRequest());

        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGstNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingLocationRepository.findAll().size();
        // set the field null
        billingLocation.setGstNumber(null);

        // Create the BillingLocation, which fails.


        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingLocation)))
            .andExpect(status().isBadRequest());

        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillingLocations() throws Exception {
        // Initialize the database
        billingLocationRepository.saveAndFlush(billingLocation);

        // Get all the billingLocationList
        restBillingLocationMockMvc.perform(get("/api/billing-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortCode").value(hasItem(DEFAULT_SHORT_CODE)))
            .andExpect(jsonPath("$.[*].panNumber").value(hasItem(DEFAULT_PAN_NUMBER)))
            .andExpect(jsonPath("$.[*].gstNumber").value(hasItem(DEFAULT_GST_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getBillingLocation() throws Exception {
        // Initialize the database
        billingLocationRepository.saveAndFlush(billingLocation);

        // Get the billingLocation
        restBillingLocationMockMvc.perform(get("/api/billing-locations/{id}", billingLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billingLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortCode").value(DEFAULT_SHORT_CODE))
            .andExpect(jsonPath("$.panNumber").value(DEFAULT_PAN_NUMBER))
            .andExpect(jsonPath("$.gstNumber").value(DEFAULT_GST_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingBillingLocation() throws Exception {
        // Get the billingLocation
        restBillingLocationMockMvc.perform(get("/api/billing-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingLocation() throws Exception {
        // Initialize the database
        billingLocationService.save(billingLocation);

        int databaseSizeBeforeUpdate = billingLocationRepository.findAll().size();

        // Update the billingLocation
        BillingLocation updatedBillingLocation = billingLocationRepository.findById(billingLocation.getId()).get();
        // Disconnect from session so that the updates on updatedBillingLocation are not directly saved in db
        em.detach(updatedBillingLocation);
        updatedBillingLocation
            .name(UPDATED_NAME)
            .shortCode(UPDATED_SHORT_CODE)
            .panNumber(UPDATED_PAN_NUMBER)
            .gstNumber(UPDATED_GST_NUMBER);

        restBillingLocationMockMvc.perform(put("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillingLocation)))
            .andExpect(status().isOk());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeUpdate);
        BillingLocation testBillingLocation = billingLocationList.get(billingLocationList.size() - 1);
        assertThat(testBillingLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBillingLocation.getShortCode()).isEqualTo(UPDATED_SHORT_CODE);
        assertThat(testBillingLocation.getPanNumber()).isEqualTo(UPDATED_PAN_NUMBER);
        assertThat(testBillingLocation.getGstNumber()).isEqualTo(UPDATED_GST_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingLocation() throws Exception {
        int databaseSizeBeforeUpdate = billingLocationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingLocationMockMvc.perform(put("/api/billing-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingLocation)))
            .andExpect(status().isBadRequest());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingLocation() throws Exception {
        // Initialize the database
        billingLocationService.save(billingLocation);

        int databaseSizeBeforeDelete = billingLocationRepository.findAll().size();

        // Delete the billingLocation
        restBillingLocationMockMvc.perform(delete("/api/billing-locations/{id}", billingLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
