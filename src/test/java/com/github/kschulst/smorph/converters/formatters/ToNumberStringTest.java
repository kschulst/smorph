package com.github.kschulst.smorph.converters.formatters;

import org.junit.Test;

import static com.github.kschulst.smorph.base.NumberFormat.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ToNumberStringTest {

    @Test
    public void fromNumber() throws Exception {
        assertThat(ToNumberString.from((Number) null, N_DOT_DDDD), is(nullValue()));
        assertThat(ToNumberString.from(10000.295, NO_DECIMALS), is("10000"));

        assertThat(ToNumberString.from(10000.295, N_DOT_DDDD), is("10000.2950"));
        assertThat(ToNumberString.from(-10000.295, N_DOT_DDDD), is("-10000.2950"));
        assertThat(ToNumberString.from(0.029, N_DOT_DDDD), is("0.0290"));

        assertThat(ToNumberString.from(10000.295, N_COMMA_DDDD), is("10000,2950"));
        assertThat(ToNumberString.from(-10000.295, N_COMMA_DDDD), is("-10000,2950"));
        assertThat(ToNumberString.from(0.029, N_COMMA_DDDD), is("0,0290"));

        assertThat(ToNumberString.from(10000.295, N_DOT_DDD), is("10000.295"));
        assertThat(ToNumberString.from(-10000.295, N_DOT_DDD), is("-10000.295"));
        assertThat(ToNumberString.from(0.029, N_DOT_DDD), is("0.029"));

        assertThat(ToNumberString.from(10000.295, N_COMMA_DDD), is("10000,295"));
        assertThat(ToNumberString.from(-10000.295, N_COMMA_DDD), is("-10000,295"));
        assertThat(ToNumberString.from(0.029, N_COMMA_DDD), is("0,029"));

        assertThat(ToNumberString.from(10000.295, N_DOT_DD), is("10000.30"));
        assertThat(ToNumberString.from(-10000.295, N_DOT_DD), is("-10000.30"));
        assertThat(ToNumberString.from(0.029, N_DOT_DD), is("0.03"));

        assertThat(ToNumberString.from(10000.295, N_COMMA_DD), is("10000,30"));
        assertThat(ToNumberString.from(-10000.295, N_COMMA_DD), is("-10000,30"));
        assertThat(ToNumberString.from(0.029, N_COMMA_DD), is("0,03"));

        assertThat(ToNumberString.from(.295, N_DOT_D), is("0.3"));
        assertThat(ToNumberString.from(10000.295, N_DOT_D), is("10000.3"));
        assertThat(ToNumberString.from(-10000.295, N_DOT_D), is("-10000.3"));
        assertThat(ToNumberString.from(0.099, N_DOT_D), is("0.1"));

        assertThat(ToNumberString.from(.295, N_COMMA_D), is("0,3"));
        assertThat(ToNumberString.from(10000.295, N_COMMA_D), is("10000,3"));
        assertThat(ToNumberString.from(-10000.295, N_COMMA_D), is("-10000,3"));
        assertThat(ToNumberString.from(0.029, N_COMMA_D), is("0,0"));
    }
}
