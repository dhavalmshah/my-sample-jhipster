package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class BillingLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingLocation.class);
        BillingLocation billingLocation1 = new BillingLocation();
        billingLocation1.setId(1L);
        BillingLocation billingLocation2 = new BillingLocation();
        billingLocation2.setId(billingLocation1.getId());
        assertThat(billingLocation1).isEqualTo(billingLocation2);
        billingLocation2.setId(2L);
        assertThat(billingLocation1).isNotEqualTo(billingLocation2);
        billingLocation1.setId(null);
        assertThat(billingLocation1).isNotEqualTo(billingLocation2);
    }
}
