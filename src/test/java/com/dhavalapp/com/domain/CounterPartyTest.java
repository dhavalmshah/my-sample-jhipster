package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class CounterPartyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CounterParty.class);
        CounterParty counterParty1 = new CounterParty();
        counterParty1.setId(1L);
        CounterParty counterParty2 = new CounterParty();
        counterParty2.setId(counterParty1.getId());
        assertThat(counterParty1).isEqualTo(counterParty2);
        counterParty2.setId(2L);
        assertThat(counterParty1).isNotEqualTo(counterParty2);
        counterParty1.setId(null);
        assertThat(counterParty1).isNotEqualTo(counterParty2);
    }
}
