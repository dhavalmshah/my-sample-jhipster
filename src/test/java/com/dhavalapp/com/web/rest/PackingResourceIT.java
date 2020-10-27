package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.Packing;
import com.dhavalapp.com.domain.Unit;
import com.dhavalapp.com.repository.PackingRepository;
import com.dhavalapp.com.service.PackingService;

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

import com.dhavalapp.com.domain.enumeration.PackingType;
/**
 * Integration tests for the {@link PackingResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PackingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final PackingType DEFAULT_PACKING_TYPE = PackingType.Drum;
    private static final PackingType UPDATED_PACKING_TYPE = PackingType.Bag;

    private static final Double DEFAULT_NET_WEIGHT = 0D;
    private static final Double UPDATED_NET_WEIGHT = 1D;

    private static final Double DEFAULT_GROSS_WEIGHT = 0D;
    private static final Double UPDATED_GROSS_WEIGHT = 1D;

    @Autowired
    private PackingRepository packingRepository;

    @Autowired
    private PackingService packingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackingMockMvc;

    private Packing packing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Packing createEntity(EntityManager em) {
        Packing packing = new Packing()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .packingType(DEFAULT_PACKING_TYPE)
            .netWeight(DEFAULT_NET_WEIGHT)
            .grossWeight(DEFAULT_GROSS_WEIGHT);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        packing.setUnit(unit);
        return packing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Packing createUpdatedEntity(EntityManager em) {
        Packing packing = new Packing()
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .packingType(UPDATED_PACKING_TYPE)
            .netWeight(UPDATED_NET_WEIGHT)
            .grossWeight(UPDATED_GROSS_WEIGHT);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createUpdatedEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        packing.setUnit(unit);
        return packing;
    }

    @BeforeEach
    public void initTest() {
        packing = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacking() throws Exception {
        int databaseSizeBeforeCreate = packingRepository.findAll().size();
        // Create the Packing
        restPackingMockMvc.perform(post("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isCreated());

        // Validate the Packing in the database
        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeCreate + 1);
        Packing testPacking = packingList.get(packingList.size() - 1);
        assertThat(testPacking.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPacking.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPacking.getPackingType()).isEqualTo(DEFAULT_PACKING_TYPE);
        assertThat(testPacking.getNetWeight()).isEqualTo(DEFAULT_NET_WEIGHT);
        assertThat(testPacking.getGrossWeight()).isEqualTo(DEFAULT_GROSS_WEIGHT);
    }

    @Test
    @Transactional
    public void createPackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packingRepository.findAll().size();

        // Create the Packing with an existing ID
        packing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackingMockMvc.perform(post("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isBadRequest());

        // Validate the Packing in the database
        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingRepository.findAll().size();
        // set the field null
        packing.setName(null);

        // Create the Packing, which fails.


        restPackingMockMvc.perform(post("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isBadRequest());

        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingRepository.findAll().size();
        // set the field null
        packing.setQuantity(null);

        // Create the Packing, which fails.


        restPackingMockMvc.perform(post("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isBadRequest());

        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPackingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingRepository.findAll().size();
        // set the field null
        packing.setPackingType(null);

        // Create the Packing, which fails.


        restPackingMockMvc.perform(post("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isBadRequest());

        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNetWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingRepository.findAll().size();
        // set the field null
        packing.setNetWeight(null);

        // Create the Packing, which fails.


        restPackingMockMvc.perform(post("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isBadRequest());

        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrossWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingRepository.findAll().size();
        // set the field null
        packing.setGrossWeight(null);

        // Create the Packing, which fails.


        restPackingMockMvc.perform(post("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isBadRequest());

        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackings() throws Exception {
        // Initialize the database
        packingRepository.saveAndFlush(packing);

        // Get all the packingList
        restPackingMockMvc.perform(get("/api/packings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packing.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].packingType").value(hasItem(DEFAULT_PACKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(DEFAULT_NET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].grossWeight").value(hasItem(DEFAULT_GROSS_WEIGHT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPacking() throws Exception {
        // Initialize the database
        packingRepository.saveAndFlush(packing);

        // Get the packing
        restPackingMockMvc.perform(get("/api/packings/{id}", packing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(packing.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.packingType").value(DEFAULT_PACKING_TYPE.toString()))
            .andExpect(jsonPath("$.netWeight").value(DEFAULT_NET_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.grossWeight").value(DEFAULT_GROSS_WEIGHT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPacking() throws Exception {
        // Get the packing
        restPackingMockMvc.perform(get("/api/packings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacking() throws Exception {
        // Initialize the database
        packingService.save(packing);

        int databaseSizeBeforeUpdate = packingRepository.findAll().size();

        // Update the packing
        Packing updatedPacking = packingRepository.findById(packing.getId()).get();
        // Disconnect from session so that the updates on updatedPacking are not directly saved in db
        em.detach(updatedPacking);
        updatedPacking
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .packingType(UPDATED_PACKING_TYPE)
            .netWeight(UPDATED_NET_WEIGHT)
            .grossWeight(UPDATED_GROSS_WEIGHT);

        restPackingMockMvc.perform(put("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacking)))
            .andExpect(status().isOk());

        // Validate the Packing in the database
        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeUpdate);
        Packing testPacking = packingList.get(packingList.size() - 1);
        assertThat(testPacking.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPacking.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPacking.getPackingType()).isEqualTo(UPDATED_PACKING_TYPE);
        assertThat(testPacking.getNetWeight()).isEqualTo(UPDATED_NET_WEIGHT);
        assertThat(testPacking.getGrossWeight()).isEqualTo(UPDATED_GROSS_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingPacking() throws Exception {
        int databaseSizeBeforeUpdate = packingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackingMockMvc.perform(put("/api/packings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packing)))
            .andExpect(status().isBadRequest());

        // Validate the Packing in the database
        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePacking() throws Exception {
        // Initialize the database
        packingService.save(packing);

        int databaseSizeBeforeDelete = packingRepository.findAll().size();

        // Delete the packing
        restPackingMockMvc.perform(delete("/api/packings/{id}", packing.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Packing> packingList = packingRepository.findAll();
        assertThat(packingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
