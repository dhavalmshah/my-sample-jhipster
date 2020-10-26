package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.Unit;
import com.dhavalapp.com.repository.UnitRepository;
import com.dhavalapp.com.service.UnitService;

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

import com.dhavalapp.com.domain.enumeration.QuantityType;
/**
 * Integration tests for the {@link UnitResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final QuantityType DEFAULT_QUANTITY_TYPE = QuantityType.VOLUME;
    private static final QuantityType UPDATED_QUANTITY_TYPE = QuantityType.WEIGHT;

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BASE_UNIT_MULTIPLIER = 1;
    private static final Integer UPDATED_BASE_UNIT_MULTIPLIER = 2;

    private static final Boolean DEFAULT_IS_SMALLER_THAN_BASE = false;
    private static final Boolean UPDATED_IS_SMALLER_THAN_BASE = true;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitService unitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitMockMvc;

    private Unit unit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createEntity(EntityManager em) {
        Unit unit = new Unit()
            .name(DEFAULT_NAME)
            .quantityType(DEFAULT_QUANTITY_TYPE)
            .fullName(DEFAULT_FULL_NAME)
            .baseUnitMultiplier(DEFAULT_BASE_UNIT_MULTIPLIER)
            .isSmallerThanBase(DEFAULT_IS_SMALLER_THAN_BASE);
        return unit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createUpdatedEntity(EntityManager em) {
        Unit unit = new Unit()
            .name(UPDATED_NAME)
            .quantityType(UPDATED_QUANTITY_TYPE)
            .fullName(UPDATED_FULL_NAME)
            .baseUnitMultiplier(UPDATED_BASE_UNIT_MULTIPLIER)
            .isSmallerThanBase(UPDATED_IS_SMALLER_THAN_BASE);
        return unit;
    }

    @BeforeEach
    public void initTest() {
        unit = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();
        // Create the Unit
        restUnitMockMvc.perform(post("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUnit.getQuantityType()).isEqualTo(DEFAULT_QUANTITY_TYPE);
        assertThat(testUnit.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testUnit.getBaseUnitMultiplier()).isEqualTo(DEFAULT_BASE_UNIT_MULTIPLIER);
        assertThat(testUnit.isIsSmallerThanBase()).isEqualTo(DEFAULT_IS_SMALLER_THAN_BASE);
    }

    @Test
    @Transactional
    public void createUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit with an existing ID
        unit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc.perform(post("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setName(null);

        // Create the Unit, which fails.


        restUnitMockMvc.perform(post("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setFullName(null);

        // Create the Unit, which fails.


        restUnitMockMvc.perform(post("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBaseUnitMultiplierIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setBaseUnitMultiplier(null);

        // Create the Unit, which fails.


        restUnitMockMvc.perform(post("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSmallerThanBaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setIsSmallerThanBase(null);

        // Create the Unit, which fails.


        restUnitMockMvc.perform(post("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the unitList
        restUnitMockMvc.perform(get("/api/units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantityType").value(hasItem(DEFAULT_QUANTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].baseUnitMultiplier").value(hasItem(DEFAULT_BASE_UNIT_MULTIPLIER)))
            .andExpect(jsonPath("$.[*].isSmallerThanBase").value(hasItem(DEFAULT_IS_SMALLER_THAN_BASE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantityType").value(DEFAULT_QUANTITY_TYPE.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.baseUnitMultiplier").value(DEFAULT_BASE_UNIT_MULTIPLIER))
            .andExpect(jsonPath("$.isSmallerThanBase").value(DEFAULT_IS_SMALLER_THAN_BASE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnit() throws Exception {
        // Initialize the database
        unitService.save(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        Unit updatedUnit = unitRepository.findById(unit.getId()).get();
        // Disconnect from session so that the updates on updatedUnit are not directly saved in db
        em.detach(updatedUnit);
        updatedUnit
            .name(UPDATED_NAME)
            .quantityType(UPDATED_QUANTITY_TYPE)
            .fullName(UPDATED_FULL_NAME)
            .baseUnitMultiplier(UPDATED_BASE_UNIT_MULTIPLIER)
            .isSmallerThanBase(UPDATED_IS_SMALLER_THAN_BASE);

        restUnitMockMvc.perform(put("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnit)))
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUnit.getQuantityType()).isEqualTo(UPDATED_QUANTITY_TYPE);
        assertThat(testUnit.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testUnit.getBaseUnitMultiplier()).isEqualTo(UPDATED_BASE_UNIT_MULTIPLIER);
        assertThat(testUnit.isIsSmallerThanBase()).isEqualTo(UPDATED_IS_SMALLER_THAN_BASE);
    }

    @Test
    @Transactional
    public void updateNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc.perform(put("/api/units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unit)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnit() throws Exception {
        // Initialize the database
        unitService.save(unit);

        int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Delete the unit
        restUnitMockMvc.perform(delete("/api/units/{id}", unit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
