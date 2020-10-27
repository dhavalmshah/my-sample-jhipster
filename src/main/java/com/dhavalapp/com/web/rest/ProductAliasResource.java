package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.ProductAlias;
import com.dhavalapp.com.service.ProductAliasService;
import com.dhavalapp.com.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dhavalapp.com.domain.ProductAlias}.
 */
@RestController
@RequestMapping("/api")
public class ProductAliasResource {

    private final Logger log = LoggerFactory.getLogger(ProductAliasResource.class);

    private static final String ENTITY_NAME = "productAlias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAliasService productAliasService;

    public ProductAliasResource(ProductAliasService productAliasService) {
        this.productAliasService = productAliasService;
    }

    /**
     * {@code POST  /product-aliases} : Create a new productAlias.
     *
     * @param productAlias the productAlias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAlias, or with status {@code 400 (Bad Request)} if the productAlias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-aliases")
    public ResponseEntity<ProductAlias> createProductAlias(@Valid @RequestBody ProductAlias productAlias) throws URISyntaxException {
        log.debug("REST request to save ProductAlias : {}", productAlias);
        if (productAlias.getId() != null) {
            throw new BadRequestAlertException("A new productAlias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAlias result = productAliasService.save(productAlias);
        return ResponseEntity.created(new URI("/api/product-aliases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-aliases} : Updates an existing productAlias.
     *
     * @param productAlias the productAlias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAlias,
     * or with status {@code 400 (Bad Request)} if the productAlias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAlias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-aliases")
    public ResponseEntity<ProductAlias> updateProductAlias(@Valid @RequestBody ProductAlias productAlias) throws URISyntaxException {
        log.debug("REST request to update ProductAlias : {}", productAlias);
        if (productAlias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAlias result = productAliasService.save(productAlias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAlias.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-aliases} : get all the productAliases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAliases in body.
     */
    @GetMapping("/product-aliases")
    public List<ProductAlias> getAllProductAliases() {
        log.debug("REST request to get all ProductAliases");
        return productAliasService.findAll();
    }

    /**
     * {@code GET  /product-aliases/:id} : get the "id" productAlias.
     *
     * @param id the id of the productAlias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAlias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-aliases/{id}")
    public ResponseEntity<ProductAlias> getProductAlias(@PathVariable Long id) {
        log.debug("REST request to get ProductAlias : {}", id);
        Optional<ProductAlias> productAlias = productAliasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAlias);
    }

    /**
     * {@code DELETE  /product-aliases/:id} : delete the "id" productAlias.
     *
     * @param id the id of the productAlias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-aliases/{id}")
    public ResponseEntity<Void> deleteProductAlias(@PathVariable Long id) {
        log.debug("REST request to delete ProductAlias : {}", id);
        productAliasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
