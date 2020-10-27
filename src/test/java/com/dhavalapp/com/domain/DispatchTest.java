package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class DispatchTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispatch.class);
        Dispatch dispatch1 = new Dispatch();
        dispatch1.setId(1L);
        Dispatch dispatch2 = new Dispatch();
        dispatch2.setId(dispatch1.getId());
        assertThat(dispatch1).isEqualTo(dispatch2);
        dispatch2.setId(2L);
        assertThat(dispatch1).isNotEqualTo(dispatch2);
        dispatch1.setId(null);
        assertThat(dispatch1).isNotEqualTo(dispatch2);
    }
}
