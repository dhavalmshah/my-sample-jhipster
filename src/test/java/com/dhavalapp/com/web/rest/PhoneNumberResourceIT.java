package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.PhoneNumber;
import com.dhavalapp.com.repository.PhoneNumberRepository;
import com.dhavalapp.com.service.PhoneNumberService;

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

import com.dhavalapp.com.domain.enumeration.ContactType;
/**
 * Integration tests for the {@link PhoneNumberResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PhoneNumberResourceIT {

    private static final ContactType DEFAULT_TYPE = ContactType.WORK;
    private static final ContactType UPDATED_TYPE = ContactType.PERSONAL;

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "5582 ";
    private static final String UPDATED_PHONE_NUMBER = "8-";

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhoneNumberMockMvc;

    private PhoneNumber phoneNumber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneNumber createEntity(EntityManager em) {
        PhoneNumber phoneNumber = new PhoneNumber()
            .type(DEFAULT_TYPE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return phoneNumber;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneNumber createUpdatedEntity(EntityManager em) {
        PhoneNumber phoneNumber = new PhoneNumber()
            .type(UPDATED_TYPE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return phoneNumber;
    }

    @BeforeEach
    public void initTest() {
        phoneNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneNumber() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberRepository.findAll().size();
        // Create the PhoneNumber
        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isCreated());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNumber testPhoneNumber = phoneNumberList.get(phoneNumberList.size() - 1);
        assertThat(testPhoneNumber.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPhoneNumber.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testPhoneNumber.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createPhoneNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberRepository.findAll().size();

        // Create the PhoneNumber with an existing ID
        phoneNumber.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setType(null);

        // Create the PhoneNumber, which fails.


        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setCountryCode(null);

        // Create the PhoneNumber, which fails.


        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberRepository.findAll().size();
        // set the field null
        phoneNumber.setPhoneNumber(null);

        // Create the PhoneNumber, which fails.


        restPhoneNumberMockMvc.perform(post("/api/phone-numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneNumbers() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get all the phoneNumberList
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getPhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", phoneNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phoneNumber.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingPhoneNumber() throws Exception {
        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phone-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberService.save(phoneNumber);

        int databaseSizeBeforeUpdate = phoneNumberRepository.findAll().size();

        // Update the phoneNumber
        PhoneNumber updatedPhoneNumber = phoneNumberRepository.findById(phoneNumber.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneNumber are not directly saved in db
        em.detach(updatedPhoneNumber);
        updatedPhoneNumber
            .type(UPDATED_TYPE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restPhoneNumberMockMvc.perform(put("/api/phone-numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhoneNumber)))
            .andExpect(status().isOk());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeUpdate);
        PhoneNumber testPhoneNumber = phoneNumberList.get(phoneNumberList.size() - 1);
        assertThat(testPhoneNumber.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPhoneNumber.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testPhoneNumber.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneNumber() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumberRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneNumberMockMvc.perform(put("/api/phone-numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberService.save(phoneNumber);

        int databaseSizeBeforeDelete = phoneNumberRepository.findAll().size();

        // Delete the phoneNumber
        restPhoneNumberMockMvc.perform(delete("/api/phone-numbers/{id}", phoneNumber.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        assertThat(phoneNumberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
