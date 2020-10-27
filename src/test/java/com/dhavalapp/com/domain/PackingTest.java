package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class PackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Packing.class);
        Packing packing1 = new Packing();
        packing1.setId(1L);
        Packing packing2 = new Packing();
        packing2.setId(packing1.getId());
        assertThat(packing1).isEqualTo(packing2);
        packing2.setId(2L);
        assertThat(packing1).isNotEqualTo(packing2);
        packing1.setId(null);
        assertThat(packing1).isNotEqualTo(packing2);
    }
}
