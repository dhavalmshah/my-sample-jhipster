package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class ProductAliasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAlias.class);
        ProductAlias productAlias1 = new ProductAlias();
        productAlias1.setId(1L);
        ProductAlias productAlias2 = new ProductAlias();
        productAlias2.setId(productAlias1.getId());
        assertThat(productAlias1).isEqualTo(productAlias2);
        productAlias2.setId(2L);
        assertThat(productAlias1).isNotEqualTo(productAlias2);
        productAlias1.setId(null);
        assertThat(productAlias1).isNotEqualTo(productAlias2);
    }
}
