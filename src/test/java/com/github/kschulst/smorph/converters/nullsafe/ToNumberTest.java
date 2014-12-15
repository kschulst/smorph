package com.github.kschulst.smorph.converters.nullsafe;

import com.google.common.primitives.UnsignedInteger;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.github.kschulst.smorph.converters.nullsafe.Fixtures.jaxbElement;
import static com.github.kschulst.smorph.converters.nullsafe.ToNumber.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ToNumberTest {

    // ------------------------------------------------------------------------
    // Integer
    // ------------------------------------------------------------------------

    @Test
    public void asInteger_fromJAXBElement() throws Exception {
        JAXBElement<Integer> jaxbElement = null;
        assertThat(asInteger(jaxbElement), is(nullValue()));

        jaxbElement = jaxbElement(Integer.class, new Integer(123));
        assertThat(asInteger(jaxbElement), is(123));
    }

    @Test
    public void asInteger_fromNumber() throws Exception {
        Number number = null;
        assertThat(asInteger(number), is(nullValue()));

        number = new Integer(123);
        assertThat(asInteger(number), is(123));
    }

    @Test
    public void asInteger_fromString() throws Exception {
        assertThat(asInteger((String) null), is(nullValue()));
        assertThat(asInteger("invalidNumber"), is(nullValue()));

        assertThat(asInteger("123"), is(123));
    }

    @Test
    public void asInteger_fromString_defaultValue() throws Exception {
        String s = null;
        assertThat(convertAsInteger(s).withDefaultValue(42), is(42));
        assertThat(convertAsInteger("invalidNumber").withDefaultValue(42), is(42));

        assertThat(asInteger("123"), is(123));
    }

    // ------------------------------------------------------------------------
    // Long
    // ------------------------------------------------------------------------

    @Test
    public void asLong_fromJAXBElement() throws Exception {
        JAXBElement<Long> jaxbElement = null;
        assertThat(asLong(jaxbElement), is(nullValue()));

        jaxbElement = jaxbElement(Long.class, new Long(123));
        assertThat(asLong(jaxbElement), is(123L));
    }

    @Test
    public void asLong_fromNumber() throws Exception {
        Number number = null;
        assertThat(asLong(number), is(nullValue()));
        assertThat(asLong("invalidNumber"), is(nullValue()));

        number = new Long(123);
        assertThat(asLong(number), is(123L));
    }

    @Test
    public void asLong_fromString() throws Exception {
        String s = null;
        assertThat(asLong(s), is(nullValue()));
        assertThat(asLong("invalidNumber"), is(nullValue()));

        assertThat(asLong("123"), is(123L));
    }

    @Test
    public void asLong_fromString_defaultValue() throws Exception {
        String s = null;
        assertThat(convertAsLong(s).withDefaultValue(42L), is(42L));
        assertThat(convertAsLong("invalidNumber").withDefaultValue(42L), is(42L));

        assertThat(asLong("123"), is(123L));
    }

    // ------------------------------------------------------------------------
    // Double
    // ------------------------------------------------------------------------

    @Test
    public void asDouble_fromJAXBElement() throws Exception {
        JAXBElement<Double> jaxbElement = null;
        assertThat(asDouble(jaxbElement), is(nullValue()));

        jaxbElement = jaxbElement(Double.class, new Double(123));
        assertThat(asDouble(jaxbElement), is(123D));
    }

    @Test
    public void asDouble_fromNumber() throws Exception {
        Number number = null;
        assertThat(asDouble(number), is(nullValue()));

        number = new Double(123);
        assertThat(asDouble(number), is(123D));
    }

    @Test
    public void asDouble_fromString() throws Exception {
        String s = null;
        assertThat(asDouble(s), is(nullValue()));
        assertThat(asDouble("invalidNumber"), is(nullValue()));

        assertThat(asDouble("123"), is(123D));
    }

    @Test
    public void asDouble_fromString_defaultValue() throws Exception {
        String s = null;
        assertThat(convertAsDouble(s).withDefaultValue(42D), is(42D));
        assertThat(convertAsDouble("invalidNumber").withDefaultValue(42D), is(42D));

        assertThat(asDouble("123"), is(123D));
    }

    // ------------------------------------------------------------------------
    // BigDecimal
    // ------------------------------------------------------------------------

    @Test
    public void asBigDecimal_fromNumber() throws Exception {
        Number number = null;
        assertThat(asBigDecimal(number), is(nullValue()));
        assertThat(asBigDecimal(Double.NaN), is(nullValue()));
        assertThat(asBigDecimal(Double.POSITIVE_INFINITY), is(nullValue()));
        assertThat(asBigDecimal(Double.NEGATIVE_INFINITY), is(nullValue()));

        assertThat(asBigDecimal(123), is(BigDecimal.valueOf(123D)));
    }

    @Test
    public void asBigDecimal_fromJAXBElement() throws Exception {
        JAXBElement<BigDecimal> jaxbElement = null;
        assertThat(asBigDecimal(jaxbElement), is(nullValue()));

        BigDecimal bigDecimal = BigDecimal.valueOf(123D);
        jaxbElement = jaxbElement(BigDecimal.class, bigDecimal);
        assertThat(asBigDecimal(jaxbElement), is(bigDecimal));
    }

    @Test
    public void asBigDecimal_fromString() throws Exception {
        BigDecimal bigDecimal = BigDecimal.valueOf(123D);
        String s = null;
        assertThat(asBigDecimal(s), is(nullValue()));
        assertThat(asBigDecimal("invalidNumber"), is(nullValue()));

        assertThat(asBigDecimal("123"), is(bigDecimal));
    }

    // ------------------------------------------------------------------------
    // BigInteger
    // ------------------------------------------------------------------------

    @Test
    public void asBigInteger_fromNumber() throws Exception {
        Number number = null;
        assertThat(asBigInteger(number), is(nullValue()));
        assertThat(asBigInteger(Double.POSITIVE_INFINITY), is(nullValue()));
        assertThat(asBigInteger(Double.NEGATIVE_INFINITY), is(nullValue()));

        BigInteger bigInteger = UnsignedInteger.valueOf(123).bigIntegerValue();
        assertThat(asBigInteger(123), is(bigInteger));
    }

    @Test
    public void asBigInteger_fromJAXBElement() throws Exception {
        JAXBElement<BigInteger> jaxbElement = null;
        assertThat(asBigInteger(jaxbElement), is(nullValue()));

        BigInteger bigInteger = UnsignedInteger.valueOf(123).bigIntegerValue();
        jaxbElement = jaxbElement(BigInteger.class, bigInteger);
        assertThat(asBigInteger(jaxbElement), is(bigInteger));
    }

    @Test
    public void asBigInteger_fromString() throws Exception {
        BigInteger bigInteger = UnsignedInteger.valueOf(123).bigIntegerValue();
        String s = null;
        assertThat(asBigInteger(s), is(nullValue()));
        assertThat(asBigInteger("invalidNumber"), is(nullValue()));

        assertThat(asBigInteger("123"), is(bigInteger));
    }
}
