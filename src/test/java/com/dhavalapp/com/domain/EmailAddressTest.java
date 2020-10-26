package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class EmailAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailAddress.class);
        EmailAddress emailAddress1 = new EmailAddress();
        emailAddress1.setId(1L);
        EmailAddress emailAddress2 = new EmailAddress();
        emailAddress2.setId(emailAddress1.getId());
        assertThat(emailAddress1).isEqualTo(emailAddress2);
        emailAddress2.setId(2L);
        assertThat(emailAddress1).isNotEqualTo(emailAddress2);
        emailAddress1.setId(null);
        assertThat(emailAddress1).isNotEqualTo(emailAddress2);
    }
}
