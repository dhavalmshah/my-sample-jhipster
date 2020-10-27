package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.GoodsReceived;
import com.dhavalapp.com.repository.GoodsReceivedRepository;
import com.dhavalapp.com.service.GoodsReceivedService;

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

import com.dhavalapp.com.domain.enumeration.GoodsReceivedStatus;
/**
 * Integration tests for the {@link GoodsReceivedResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GoodsReceivedResourceIT {

    private static final GoodsReceivedStatus DEFAULT_STATUS = GoodsReceivedStatus.ORDERED;
    private static final GoodsReceivedStatus UPDATED_STATUS = GoodsReceivedStatus.RECEIVED;

    @Autowired
    private GoodsReceivedRepository goodsReceivedRepository;

    @Autowired
    private GoodsReceivedService goodsReceivedService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoodsReceivedMockMvc;

    private GoodsReceived goodsReceived;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoodsReceived createEntity(EntityManager em) {
        GoodsReceived goodsReceived = new GoodsReceived()
            .status(DEFAULT_STATUS);
        return goodsReceived;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoodsReceived createUpdatedEntity(EntityManager em) {
        GoodsReceived goodsReceived = new GoodsReceived()
            .status(UPDATED_STATUS);
        return goodsReceived;
    }

    @BeforeEach
    public void initTest() {
        goodsReceived = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoodsReceived() throws Exception {
        int databaseSizeBeforeCreate = goodsReceivedRepository.findAll().size();
        // Create the GoodsReceived
        restGoodsReceivedMockMvc.perform(post("/api/goods-receiveds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodsReceived)))
            .andExpect(status().isCreated());

        // Validate the GoodsReceived in the database
        List<GoodsReceived> goodsReceivedList = goodsReceivedRepository.findAll();
        assertThat(goodsReceivedList).hasSize(databaseSizeBeforeCreate + 1);
        GoodsReceived testGoodsReceived = goodsReceivedList.get(goodsReceivedList.size() - 1);
        assertThat(testGoodsReceived.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createGoodsReceivedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsReceivedRepository.findAll().size();

        // Create the GoodsReceived with an existing ID
        goodsReceived.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsReceivedMockMvc.perform(post("/api/goods-receiveds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodsReceived)))
            .andExpect(status().isBadRequest());

        // Validate the GoodsReceived in the database
        List<GoodsReceived> goodsReceivedList = goodsReceivedRepository.findAll();
        assertThat(goodsReceivedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsReceivedRepository.findAll().size();
        // set the field null
        goodsReceived.setStatus(null);

        // Create the GoodsReceived, which fails.


        restGoodsReceivedMockMvc.perform(post("/api/goods-receiveds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodsReceived)))
            .andExpect(status().isBadRequest());

        List<GoodsReceived> goodsReceivedList = goodsReceivedRepository.findAll();
        assertThat(goodsReceivedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoodsReceiveds() throws Exception {
        // Initialize the database
        goodsReceivedRepository.saveAndFlush(goodsReceived);

        // Get all the goodsReceivedList
        restGoodsReceivedMockMvc.perform(get("/api/goods-receiveds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodsReceived.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getGoodsReceived() throws Exception {
        // Initialize the database
        goodsReceivedRepository.saveAndFlush(goodsReceived);

        // Get the goodsReceived
        restGoodsReceivedMockMvc.perform(get("/api/goods-receiveds/{id}", goodsReceived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goodsReceived.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGoodsReceived() throws Exception {
        // Get the goodsReceived
        restGoodsReceivedMockMvc.perform(get("/api/goods-receiveds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoodsReceived() throws Exception {
        // Initialize the database
        goodsReceivedService.save(goodsReceived);

        int databaseSizeBeforeUpdate = goodsReceivedRepository.findAll().size();

        // Update the goodsReceived
        GoodsReceived updatedGoodsReceived = goodsReceivedRepository.findById(goodsReceived.getId()).get();
        // Disconnect from session so that the updates on updatedGoodsReceived are not directly saved in db
        em.detach(updatedGoodsReceived);
        updatedGoodsReceived
            .status(UPDATED_STATUS);

        restGoodsReceivedMockMvc.perform(put("/api/goods-receiveds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoodsReceived)))
            .andExpect(status().isOk());

        // Validate the GoodsReceived in the database
        List<GoodsReceived> goodsReceivedList = goodsReceivedRepository.findAll();
        assertThat(goodsReceivedList).hasSize(databaseSizeBeforeUpdate);
        GoodsReceived testGoodsReceived = goodsReceivedList.get(goodsReceivedList.size() - 1);
        assertThat(testGoodsReceived.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingGoodsReceived() throws Exception {
        int databaseSizeBeforeUpdate = goodsReceivedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsReceivedMockMvc.perform(put("/api/goods-receiveds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodsReceived)))
            .andExpect(status().isBadRequest());

        // Validate the GoodsReceived in the database
        List<GoodsReceived> goodsReceivedList = goodsReceivedRepository.findAll();
        assertThat(goodsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoodsReceived() throws Exception {
        // Initialize the database
        goodsReceivedService.save(goodsReceived);

        int databaseSizeBeforeDelete = goodsReceivedRepository.findAll().size();

        // Delete the goodsReceived
        restGoodsReceivedMockMvc.perform(delete("/api/goods-receiveds/{id}", goodsReceived.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoodsReceived> goodsReceivedList = goodsReceivedRepository.findAll();
        assertThat(goodsReceivedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
