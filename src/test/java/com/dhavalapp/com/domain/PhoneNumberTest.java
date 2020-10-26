package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class PhoneNumberTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumber.class);
        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setId(1L);
        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setId(phoneNumber1.getId());
        assertThat(phoneNumber1).isEqualTo(phoneNumber2);
        phoneNumber2.setId(2L);
        assertThat(phoneNumber1).isNotEqualTo(phoneNumber2);
        phoneNumber1.setId(null);
        assertThat(phoneNumber1).isNotEqualTo(phoneNumber2);
    }
}
