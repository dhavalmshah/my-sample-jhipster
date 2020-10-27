package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.ProductAlias;
import com.dhavalapp.com.repository.ProductAliasRepository;
import com.dhavalapp.com.service.ProductAliasService;

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
 * Integration tests for the {@link ProductAliasResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductAliasResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductAliasRepository productAliasRepository;

    @Autowired
    private ProductAliasService productAliasService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAliasMockMvc;

    private ProductAlias productAlias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAlias createEntity(EntityManager em) {
        ProductAlias productAlias = new ProductAlias()
            .name(DEFAULT_NAME);
        return productAlias;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAlias createUpdatedEntity(EntityManager em) {
        ProductAlias productAlias = new ProductAlias()
            .name(UPDATED_NAME);
        return productAlias;
    }

    @BeforeEach
    public void initTest() {
        productAlias = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAlias() throws Exception {
        int databaseSizeBeforeCreate = productAliasRepository.findAll().size();
        // Create the ProductAlias
        restProductAliasMockMvc.perform(post("/api/product-aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAlias)))
            .andExpect(status().isCreated());

        // Validate the ProductAlias in the database
        List<ProductAlias> productAliasList = productAliasRepository.findAll();
        assertThat(productAliasList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAlias testProductAlias = productAliasList.get(productAliasList.size() - 1);
        assertThat(testProductAlias.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductAliasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAliasRepository.findAll().size();

        // Create the ProductAlias with an existing ID
        productAlias.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAliasMockMvc.perform(post("/api/product-aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAlias)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAlias in the database
        List<ProductAlias> productAliasList = productAliasRepository.findAll();
        assertThat(productAliasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAliasRepository.findAll().size();
        // set the field null
        productAlias.setName(null);

        // Create the ProductAlias, which fails.


        restProductAliasMockMvc.perform(post("/api/product-aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAlias)))
            .andExpect(status().isBadRequest());

        List<ProductAlias> productAliasList = productAliasRepository.findAll();
        assertThat(productAliasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAliases() throws Exception {
        // Initialize the database
        productAliasRepository.saveAndFlush(productAlias);

        // Get all the productAliasList
        restProductAliasMockMvc.perform(get("/api/product-aliases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAlias.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductAlias() throws Exception {
        // Initialize the database
        productAliasRepository.saveAndFlush(productAlias);

        // Get the productAlias
        restProductAliasMockMvc.perform(get("/api/product-aliases/{id}", productAlias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAlias.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingProductAlias() throws Exception {
        // Get the productAlias
        restProductAliasMockMvc.perform(get("/api/product-aliases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAlias() throws Exception {
        // Initialize the database
        productAliasService.save(productAlias);

        int databaseSizeBeforeUpdate = productAliasRepository.findAll().size();

        // Update the productAlias
        ProductAlias updatedProductAlias = productAliasRepository.findById(productAlias.getId()).get();
        // Disconnect from session so that the updates on updatedProductAlias are not directly saved in db
        em.detach(updatedProductAlias);
        updatedProductAlias
            .name(UPDATED_NAME);

        restProductAliasMockMvc.perform(put("/api/product-aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAlias)))
            .andExpect(status().isOk());

        // Validate the ProductAlias in the database
        List<ProductAlias> productAliasList = productAliasRepository.findAll();
        assertThat(productAliasList).hasSize(databaseSizeBeforeUpdate);
        ProductAlias testProductAlias = productAliasList.get(productAliasList.size() - 1);
        assertThat(testProductAlias.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAlias() throws Exception {
        int databaseSizeBeforeUpdate = productAliasRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAliasMockMvc.perform(put("/api/product-aliases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAlias)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAlias in the database
        List<ProductAlias> productAliasList = productAliasRepository.findAll();
        assertThat(productAliasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAlias() throws Exception {
        // Initialize the database
        productAliasService.save(productAlias);

        int databaseSizeBeforeDelete = productAliasRepository.findAll().size();

        // Delete the productAlias
        restProductAliasMockMvc.perform(delete("/api/product-aliases/{id}", productAlias.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAlias> productAliasList = productAliasRepository.findAll();
        assertThat(productAliasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
