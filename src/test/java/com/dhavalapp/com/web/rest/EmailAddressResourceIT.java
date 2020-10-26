package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.EmailAddress;
import com.dhavalapp.com.repository.EmailAddressRepository;
import com.dhavalapp.com.service.EmailAddressService;

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
 * Integration tests for the {@link EmailAddressResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmailAddressResourceIT {

    private static final ContactType DEFAULT_TYPE = ContactType.WORK;
    private static final ContactType UPDATED_TYPE = ContactType.PERSONAL;

    private static final String DEFAULT_EMAIL_ADDRESS = "+~5@SldEN.";
    private static final String UPDATED_EMAIL_ADDRESS = "bHw8wH@Uw43zQ.--rk0uDLDaEbP";

    @Autowired
    private EmailAddressRepository emailAddressRepository;

    @Autowired
    private EmailAddressService emailAddressService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailAddressMockMvc;

    private EmailAddress emailAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailAddress createEntity(EntityManager em) {
        EmailAddress emailAddress = new EmailAddress()
            .type(DEFAULT_TYPE)
            .emailAddress(DEFAULT_EMAIL_ADDRESS);
        return emailAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailAddress createUpdatedEntity(EntityManager em) {
        EmailAddress emailAddress = new EmailAddress()
            .type(UPDATED_TYPE)
            .emailAddress(UPDATED_EMAIL_ADDRESS);
        return emailAddress;
    }

    @BeforeEach
    public void initTest() {
        emailAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailAddress() throws Exception {
        int databaseSizeBeforeCreate = emailAddressRepository.findAll().size();
        // Create the EmailAddress
        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isCreated());

        // Validate the EmailAddress in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeCreate + 1);
        EmailAddress testEmailAddress = emailAddressList.get(emailAddressList.size() - 1);
        assertThat(testEmailAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmailAddress.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void createEmailAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailAddressRepository.findAll().size();

        // Create the EmailAddress with an existing ID
        emailAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        // Validate the EmailAddress in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAddressRepository.findAll().size();
        // set the field null
        emailAddress.setType(null);

        // Create the EmailAddress, which fails.


        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailAddressRepository.findAll().size();
        // set the field null
        emailAddress.setEmailAddress(null);

        // Create the EmailAddress, which fails.


        restEmailAddressMockMvc.perform(post("/api/email-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailAddresses() throws Exception {
        // Initialize the database
        emailAddressRepository.saveAndFlush(emailAddress);

        // Get all the emailAddressList
        restEmailAddressMockMvc.perform(get("/api/email-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getEmailAddress() throws Exception {
        // Initialize the database
        emailAddressRepository.saveAndFlush(emailAddress);

        // Get the emailAddress
        restEmailAddressMockMvc.perform(get("/api/email-addresses/{id}", emailAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailAddress.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingEmailAddress() throws Exception {
        // Get the emailAddress
        restEmailAddressMockMvc.perform(get("/api/email-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailAddress() throws Exception {
        // Initialize the database
        emailAddressService.save(emailAddress);

        int databaseSizeBeforeUpdate = emailAddressRepository.findAll().size();

        // Update the emailAddress
        EmailAddress updatedEmailAddress = emailAddressRepository.findById(emailAddress.getId()).get();
        // Disconnect from session so that the updates on updatedEmailAddress are not directly saved in db
        em.detach(updatedEmailAddress);
        updatedEmailAddress
            .type(UPDATED_TYPE)
            .emailAddress(UPDATED_EMAIL_ADDRESS);

        restEmailAddressMockMvc.perform(put("/api/email-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmailAddress)))
            .andExpect(status().isOk());

        // Validate the EmailAddress in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeUpdate);
        EmailAddress testEmailAddress = emailAddressList.get(emailAddressList.size() - 1);
        assertThat(testEmailAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmailAddress.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailAddress() throws Exception {
        int databaseSizeBeforeUpdate = emailAddressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailAddressMockMvc.perform(put("/api/email-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailAddress)))
            .andExpect(status().isBadRequest());

        // Validate the EmailAddress in the database
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailAddress() throws Exception {
        // Initialize the database
        emailAddressService.save(emailAddress);

        int databaseSizeBeforeDelete = emailAddressRepository.findAll().size();

        // Delete the emailAddress
        restEmailAddressMockMvc.perform(delete("/api/email-addresses/{id}", emailAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailAddress> emailAddressList = emailAddressRepository.findAll();
        assertThat(emailAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
