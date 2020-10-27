package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.MySampleApp;
import com.dhavalapp.com.domain.StockTransaction;
import com.dhavalapp.com.repository.StockTransactionRepository;
import com.dhavalapp.com.service.StockTransactionService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dhavalapp.com.domain.enumeration.StockTransactionType;
/**
 * Integration tests for the {@link StockTransactionResource} REST controller.
 */
@SpringBootTest(classes = MySampleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StockTransactionResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StockTransactionType DEFAULT_TRANSACTION_TYPE = StockTransactionType.PURCHASE;
    private static final StockTransactionType UPDATED_TRANSACTION_TYPE = StockTransactionType.SALE;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private StockTransactionService stockTransactionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockTransactionMockMvc;

    private StockTransaction stockTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockTransaction createEntity(EntityManager em) {
        StockTransaction stockTransaction = new StockTransaction()
            .description(DEFAULT_DESCRIPTION)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .transactionType(DEFAULT_TRANSACTION_TYPE);
        return stockTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockTransaction createUpdatedEntity(EntityManager em) {
        StockTransaction stockTransaction = new StockTransaction()
            .description(UPDATED_DESCRIPTION)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionType(UPDATED_TRANSACTION_TYPE);
        return stockTransaction;
    }

    @BeforeEach
    public void initTest() {
        stockTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockTransaction() throws Exception {
        int databaseSizeBeforeCreate = stockTransactionRepository.findAll().size();
        // Create the StockTransaction
        restStockTransactionMockMvc.perform(post("/api/stock-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isCreated());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        StockTransaction testStockTransaction = stockTransactionList.get(stockTransactionList.size() - 1);
        assertThat(testStockTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStockTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testStockTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void createStockTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockTransactionRepository.findAll().size();

        // Create the StockTransaction with an existing ID
        stockTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockTransactionMockMvc.perform(post("/api/stock-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockTransactionRepository.findAll().size();
        // set the field null
        stockTransaction.setDescription(null);

        // Create the StockTransaction, which fails.


        restStockTransactionMockMvc.perform(post("/api/stock-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockTransactionRepository.findAll().size();
        // set the field null
        stockTransaction.setTransactionDate(null);

        // Create the StockTransaction, which fails.


        restStockTransactionMockMvc.perform(post("/api/stock-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockTransactionRepository.findAll().size();
        // set the field null
        stockTransaction.setTransactionType(null);

        // Create the StockTransaction, which fails.


        restStockTransactionMockMvc.perform(post("/api/stock-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockTransactions() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList
        restStockTransactionMockMvc.perform(get("/api/stock-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getStockTransaction() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get the stockTransaction
        restStockTransactionMockMvc.perform(get("/api/stock-transactions/{id}", stockTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockTransaction.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStockTransaction() throws Exception {
        // Get the stockTransaction
        restStockTransactionMockMvc.perform(get("/api/stock-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockTransaction() throws Exception {
        // Initialize the database
        stockTransactionService.save(stockTransaction);

        int databaseSizeBeforeUpdate = stockTransactionRepository.findAll().size();

        // Update the stockTransaction
        StockTransaction updatedStockTransaction = stockTransactionRepository.findById(stockTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedStockTransaction are not directly saved in db
        em.detach(updatedStockTransaction);
        updatedStockTransaction
            .description(UPDATED_DESCRIPTION)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionType(UPDATED_TRANSACTION_TYPE);

        restStockTransactionMockMvc.perform(put("/api/stock-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockTransaction)))
            .andExpect(status().isOk());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeUpdate);
        StockTransaction testStockTransaction = stockTransactionList.get(stockTransactionList.size() - 1);
        assertThat(testStockTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStockTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testStockTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingStockTransaction() throws Exception {
        int databaseSizeBeforeUpdate = stockTransactionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockTransactionMockMvc.perform(put("/api/stock-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockTransaction() throws Exception {
        // Initialize the database
        stockTransactionService.save(stockTransaction);

        int databaseSizeBeforeDelete = stockTransactionRepository.findAll().size();

        // Delete the stockTransaction
        restStockTransactionMockMvc.perform(delete("/api/stock-transactions/{id}", stockTransaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
