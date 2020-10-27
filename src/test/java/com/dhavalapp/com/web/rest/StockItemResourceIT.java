package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.StockItem;
import com.dhavalapp.com.domain.Unit;
import com.dhavalapp.com.domain.Packing;
import com.dhavalapp.com.domain.Product;
import com.dhavalapp.com.domain.BillingLocation;
import com.dhavalapp.com.repository.StockItemRepository;
import com.dhavalapp.com.service.StockItemService;

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

import com.dhavalapp.com.domain.enumeration.StockStatus;
/**
 * Integration tests for the {@link StockItemResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StockItemResourceIT {

    private static final StockStatus DEFAULT_STATUS = StockStatus.CANPLAN;
    private static final StockStatus UPDATED_STATUS = StockStatus.TESTING;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_BATCH_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_NUMBER = "BBBBBBBBBB";

    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private StockItemService stockItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockItemMockMvc;

    private StockItem stockItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItem createEntity(EntityManager em) {
        StockItem stockItem = new StockItem()
            .status(DEFAULT_STATUS)
            .quantity(DEFAULT_QUANTITY)
            .batchNumber(DEFAULT_BATCH_NUMBER);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        stockItem.setUnit(unit);
        // Add required entity
        Packing packing;
        if (TestUtil.findAll(em, Packing.class).isEmpty()) {
            packing = PackingResourceIT.createEntity(em);
            em.persist(packing);
            em.flush();
        } else {
            packing = TestUtil.findAll(em, Packing.class).get(0);
        }
        stockItem.setPacking(packing);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        stockItem.setProduct(product);
        // Add required entity
        BillingLocation billingLocation;
        if (TestUtil.findAll(em, BillingLocation.class).isEmpty()) {
            billingLocation = BillingLocationResourceIT.createEntity(em);
            em.persist(billingLocation);
            em.flush();
        } else {
            billingLocation = TestUtil.findAll(em, BillingLocation.class).get(0);
        }
        stockItem.setLocation(billingLocation);
        return stockItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItem createUpdatedEntity(EntityManager em) {
        StockItem stockItem = new StockItem()
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .batchNumber(UPDATED_BATCH_NUMBER);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createUpdatedEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        stockItem.setUnit(unit);
        // Add required entity
        Packing packing;
        if (TestUtil.findAll(em, Packing.class).isEmpty()) {
            packing = PackingResourceIT.createUpdatedEntity(em);
            em.persist(packing);
            em.flush();
        } else {
            packing = TestUtil.findAll(em, Packing.class).get(0);
        }
        stockItem.setPacking(packing);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        stockItem.setProduct(product);
        // Add required entity
        BillingLocation billingLocation;
        if (TestUtil.findAll(em, BillingLocation.class).isEmpty()) {
            billingLocation = BillingLocationResourceIT.createUpdatedEntity(em);
            em.persist(billingLocation);
            em.flush();
        } else {
            billingLocation = TestUtil.findAll(em, BillingLocation.class).get(0);
        }
        stockItem.setLocation(billingLocation);
        return stockItem;
    }

    @BeforeEach
    public void initTest() {
        stockItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItem() throws Exception {
        int databaseSizeBeforeCreate = stockItemRepository.findAll().size();
        // Create the StockItem
        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isCreated());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeCreate + 1);
        StockItem testStockItem = stockItemList.get(stockItemList.size() - 1);
        assertThat(testStockItem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStockItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStockItem.getBatchNumber()).isEqualTo(DEFAULT_BATCH_NUMBER);
    }

    @Test
    @Transactional
    public void createStockItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemRepository.findAll().size();

        // Create the StockItem with an existing ID
        stockItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemRepository.findAll().size();
        // set the field null
        stockItem.setStatus(null);

        // Create the StockItem, which fails.


        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemRepository.findAll().size();
        // set the field null
        stockItem.setQuantity(null);

        // Create the StockItem, which fails.


        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBatchNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemRepository.findAll().size();
        // set the field null
        stockItem.setBatchNumber(null);

        // Create the StockItem, which fails.


        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItems() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList
        restStockItemMockMvc.perform(get("/api/stock-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].batchNumber").value(hasItem(DEFAULT_BATCH_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getStockItem() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get the stockItem
        restStockItemMockMvc.perform(get("/api/stock-items/{id}", stockItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockItem.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.batchNumber").value(DEFAULT_BATCH_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingStockItem() throws Exception {
        // Get the stockItem
        restStockItemMockMvc.perform(get("/api/stock-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItem() throws Exception {
        // Initialize the database
        stockItemService.save(stockItem);

        int databaseSizeBeforeUpdate = stockItemRepository.findAll().size();

        // Update the stockItem
        StockItem updatedStockItem = stockItemRepository.findById(stockItem.getId()).get();
        // Disconnect from session so that the updates on updatedStockItem are not directly saved in db
        em.detach(updatedStockItem);
        updatedStockItem
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .batchNumber(UPDATED_BATCH_NUMBER);

        restStockItemMockMvc.perform(put("/api/stock-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockItem)))
            .andExpect(status().isOk());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeUpdate);
        StockItem testStockItem = stockItemList.get(stockItemList.size() - 1);
        assertThat(testStockItem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStockItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStockItem.getBatchNumber()).isEqualTo(UPDATED_BATCH_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItem() throws Exception {
        int databaseSizeBeforeUpdate = stockItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemMockMvc.perform(put("/api/stock-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItem() throws Exception {
        // Initialize the database
        stockItemService.save(stockItem);

        int databaseSizeBeforeDelete = stockItemRepository.findAll().size();

        // Delete the stockItem
        restStockItemMockMvc.perform(delete("/api/stock-items/{id}", stockItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
