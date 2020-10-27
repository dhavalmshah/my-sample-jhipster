package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.Dispatch;
import com.dhavalapp.com.repository.DispatchRepository;
import com.dhavalapp.com.service.DispatchService;

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

import com.dhavalapp.com.domain.enumeration.GoodsDispatchStatus;
/**
 * Integration tests for the {@link DispatchResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DispatchResourceIT {

    private static final GoodsDispatchStatus DEFAULT_STATUS = GoodsDispatchStatus.PLANNED;
    private static final GoodsDispatchStatus UPDATED_STATUS = GoodsDispatchStatus.APPROVED;

    @Autowired
    private DispatchRepository dispatchRepository;

    @Autowired
    private DispatchService dispatchService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispatchMockMvc;

    private Dispatch dispatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispatch createEntity(EntityManager em) {
        Dispatch dispatch = new Dispatch()
            .status(DEFAULT_STATUS);
        return dispatch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispatch createUpdatedEntity(EntityManager em) {
        Dispatch dispatch = new Dispatch()
            .status(UPDATED_STATUS);
        return dispatch;
    }

    @BeforeEach
    public void initTest() {
        dispatch = createEntity(em);
    }

    @Test
    @Transactional
    public void createDispatch() throws Exception {
        int databaseSizeBeforeCreate = dispatchRepository.findAll().size();
        // Create the Dispatch
        restDispatchMockMvc.perform(post("/api/dispatches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dispatch)))
            .andExpect(status().isCreated());

        // Validate the Dispatch in the database
        List<Dispatch> dispatchList = dispatchRepository.findAll();
        assertThat(dispatchList).hasSize(databaseSizeBeforeCreate + 1);
        Dispatch testDispatch = dispatchList.get(dispatchList.size() - 1);
        assertThat(testDispatch.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDispatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dispatchRepository.findAll().size();

        // Create the Dispatch with an existing ID
        dispatch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispatchMockMvc.perform(post("/api/dispatches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dispatch)))
            .andExpect(status().isBadRequest());

        // Validate the Dispatch in the database
        List<Dispatch> dispatchList = dispatchRepository.findAll();
        assertThat(dispatchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = dispatchRepository.findAll().size();
        // set the field null
        dispatch.setStatus(null);

        // Create the Dispatch, which fails.


        restDispatchMockMvc.perform(post("/api/dispatches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dispatch)))
            .andExpect(status().isBadRequest());

        List<Dispatch> dispatchList = dispatchRepository.findAll();
        assertThat(dispatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDispatches() throws Exception {
        // Initialize the database
        dispatchRepository.saveAndFlush(dispatch);

        // Get all the dispatchList
        restDispatchMockMvc.perform(get("/api/dispatches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getDispatch() throws Exception {
        // Initialize the database
        dispatchRepository.saveAndFlush(dispatch);

        // Get the dispatch
        restDispatchMockMvc.perform(get("/api/dispatches/{id}", dispatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dispatch.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDispatch() throws Exception {
        // Get the dispatch
        restDispatchMockMvc.perform(get("/api/dispatches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDispatch() throws Exception {
        // Initialize the database
        dispatchService.save(dispatch);

        int databaseSizeBeforeUpdate = dispatchRepository.findAll().size();

        // Update the dispatch
        Dispatch updatedDispatch = dispatchRepository.findById(dispatch.getId()).get();
        // Disconnect from session so that the updates on updatedDispatch are not directly saved in db
        em.detach(updatedDispatch);
        updatedDispatch
            .status(UPDATED_STATUS);

        restDispatchMockMvc.perform(put("/api/dispatches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDispatch)))
            .andExpect(status().isOk());

        // Validate the Dispatch in the database
        List<Dispatch> dispatchList = dispatchRepository.findAll();
        assertThat(dispatchList).hasSize(databaseSizeBeforeUpdate);
        Dispatch testDispatch = dispatchList.get(dispatchList.size() - 1);
        assertThat(testDispatch.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDispatch() throws Exception {
        int databaseSizeBeforeUpdate = dispatchRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispatchMockMvc.perform(put("/api/dispatches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dispatch)))
            .andExpect(status().isBadRequest());

        // Validate the Dispatch in the database
        List<Dispatch> dispatchList = dispatchRepository.findAll();
        assertThat(dispatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDispatch() throws Exception {
        // Initialize the database
        dispatchService.save(dispatch);

        int databaseSizeBeforeDelete = dispatchRepository.findAll().size();

        // Delete the dispatch
        restDispatchMockMvc.perform(delete("/api/dispatches/{id}", dispatch.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dispatch> dispatchList = dispatchRepository.findAll();
        assertThat(dispatchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
