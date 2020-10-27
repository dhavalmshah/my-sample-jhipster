package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class TransportDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportDetails.class);
        TransportDetails transportDetails1 = new TransportDetails();
        transportDetails1.setId(1L);
        TransportDetails transportDetails2 = new TransportDetails();
        transportDetails2.setId(transportDetails1.getId());
        assertThat(transportDetails1).isEqualTo(transportDetails2);
        transportDetails2.setId(2L);
        assertThat(transportDetails1).isNotEqualTo(transportDetails2);
        transportDetails1.setId(null);
        assertThat(transportDetails1).isNotEqualTo(transportDetails2);
    }
}
