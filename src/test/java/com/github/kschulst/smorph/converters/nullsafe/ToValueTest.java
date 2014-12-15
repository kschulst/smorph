package com.github.kschulst.smorph.converters.nullsafe;

import org.junit.Test;

import javax.xml.bind.JAXBElement;

import static com.github.kschulst.smorph.converters.nullsafe.Fixtures.jaxbElement;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ToValueTest {

    @Test
    public void fromJAXBElement() throws Exception {
        JAXBElement<String> jaxbElement = null;
        assertThat(ToValue.from(jaxbElement), is(nullValue()));

        jaxbElement = jaxbElement(String.class, "test");
        assertThat(ToValue.from(jaxbElement), is("test"));
    }

}
