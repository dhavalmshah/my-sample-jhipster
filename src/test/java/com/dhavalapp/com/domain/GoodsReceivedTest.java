package com.dhavalapp.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dhavalapp.com.web.rest.TestUtil;

public class GoodsReceivedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsReceived.class);
        GoodsReceived goodsReceived1 = new GoodsReceived();
        goodsReceived1.setId(1L);
        GoodsReceived goodsReceived2 = new GoodsReceived();
        goodsReceived2.setId(goodsReceived1.getId());
        assertThat(goodsReceived1).isEqualTo(goodsReceived2);
        goodsReceived2.setId(2L);
        assertThat(goodsReceived1).isNotEqualTo(goodsReceived2);
        goodsReceived1.setId(null);
        assertThat(goodsReceived1).isNotEqualTo(goodsReceived2);
    }
}
