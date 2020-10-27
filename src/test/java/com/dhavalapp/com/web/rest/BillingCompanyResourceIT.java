package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.BillingCompany;
import com.dhavalapp.com.domain.BillingLocation;
import com.dhavalapp.com.repository.BillingCompanyRepository;
import com.dhavalapp.com.service.BillingCompanyService;

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
 * Integration tests for the {@link BillingCompanyResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BillingCompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private BillingCompanyRepository billingCompanyRepository;

    @Autowired
    private BillingCompanyService billingCompanyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillingCompanyMockMvc;

    private BillingCompany billingCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingCompany createEntity(EntityManager em) {
        BillingCompany billingCompany = new BillingCompany()
            .name(DEFAULT_NAME)
            .notes(DEFAULT_NOTES);
        // Add required entity
        BillingLocation billingLocation;
        if (TestUtil.findAll(em, BillingLocation.class).isEmpty()) {
            billingLocation = BillingLocationResourceIT.createEntity(em);
            em.persist(billingLocation);
            em.flush();
        } else {
            billingLocation = TestUtil.findAll(em, BillingLocation.class).get(0);
        }
        billingCompany.getLocations().add(billingLocation);
        return billingCompany;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingCompany createUpdatedEntity(EntityManager em) {
        BillingCompany billingCompany = new BillingCompany()
            .name(UPDATED_NAME)
            .notes(UPDATED_NOTES);
        // Add required entity
        BillingLocation billingLocation;
        if (TestUtil.findAll(em, BillingLocation.class).isEmpty()) {
            billingLocation = BillingLocationResourceIT.createUpdatedEntity(em);
            em.persist(billingLocation);
            em.flush();
        } else {
            billingLocation = TestUtil.findAll(em, BillingLocation.class).get(0);
        }
        billingCompany.getLocations().add(billingLocation);
        return billingCompany;
    }

    @BeforeEach
    public void initTest() {
        billingCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingCompany() throws Exception {
        int databaseSizeBeforeCreate = billingCompanyRepository.findAll().size();
        // Create the BillingCompany
        restBillingCompanyMockMvc.perform(post("/api/billing-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingCompany)))
            .andExpect(status().isCreated());

        // Validate the BillingCompany in the database
        List<BillingCompany> billingCompanyList = billingCompanyRepository.findAll();
        assertThat(billingCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        BillingCompany testBillingCompany = billingCompanyList.get(billingCompanyList.size() - 1);
        assertThat(testBillingCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBillingCompany.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createBillingCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingCompanyRepository.findAll().size();

        // Create the BillingCompany with an existing ID
        billingCompany.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingCompanyMockMvc.perform(post("/api/billing-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingCompany)))
            .andExpect(status().isBadRequest());

        // Validate the BillingCompany in the database
        List<BillingCompany> billingCompanyList = billingCompanyRepository.findAll();
        assertThat(billingCompanyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingCompanyRepository.findAll().size();
        // set the field null
        billingCompany.setName(null);

        // Create the BillingCompany, which fails.


        restBillingCompanyMockMvc.perform(post("/api/billing-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingCompany)))
            .andExpect(status().isBadRequest());

        List<BillingCompany> billingCompanyList = billingCompanyRepository.findAll();
        assertThat(billingCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillingCompanies() throws Exception {
        // Initialize the database
        billingCompanyRepository.saveAndFlush(billingCompany);

        // Get all the billingCompanyList
        restBillingCompanyMockMvc.perform(get("/api/billing-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }
    
    @Test
    @Transactional
    public void getBillingCompany() throws Exception {
        // Initialize the database
        billingCompanyRepository.saveAndFlush(billingCompany);

        // Get the billingCompany
        restBillingCompanyMockMvc.perform(get("/api/billing-companies/{id}", billingCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billingCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }
    @Test
    @Transactional
    public void getNonExistingBillingCompany() throws Exception {
        // Get the billingCompany
        restBillingCompanyMockMvc.perform(get("/api/billing-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingCompany() throws Exception {
        // Initialize the database
        billingCompanyService.save(billingCompany);

        int databaseSizeBeforeUpdate = billingCompanyRepository.findAll().size();

        // Update the billingCompany
        BillingCompany updatedBillingCompany = billingCompanyRepository.findById(billingCompany.getId()).get();
        // Disconnect from session so that the updates on updatedBillingCompany are not directly saved in db
        em.detach(updatedBillingCompany);
        updatedBillingCompany
            .name(UPDATED_NAME)
            .notes(UPDATED_NOTES);

        restBillingCompanyMockMvc.perform(put("/api/billing-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillingCompany)))
            .andExpect(status().isOk());

        // Validate the BillingCompany in the database
        List<BillingCompany> billingCompanyList = billingCompanyRepository.findAll();
        assertThat(billingCompanyList).hasSize(databaseSizeBeforeUpdate);
        BillingCompany testBillingCompany = billingCompanyList.get(billingCompanyList.size() - 1);
        assertThat(testBillingCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBillingCompany.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingCompany() throws Exception {
        int databaseSizeBeforeUpdate = billingCompanyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingCompanyMockMvc.perform(put("/api/billing-companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingCompany)))
            .andExpect(status().isBadRequest());

        // Validate the BillingCompany in the database
        List<BillingCompany> billingCompanyList = billingCompanyRepository.findAll();
        assertThat(billingCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingCompany() throws Exception {
        // Initialize the database
        billingCompanyService.save(billingCompany);

        int databaseSizeBeforeDelete = billingCompanyRepository.findAll().size();

        // Delete the billingCompany
        restBillingCompanyMockMvc.perform(delete("/api/billing-companies/{id}", billingCompany.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillingCompany> billingCompanyList = billingCompanyRepository.findAll();
        assertThat(billingCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
