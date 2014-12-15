package com.github.kschulst.smorph.base;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TemporalFormatTest {

    @Test
    public void validateExamplesIntegrity() {
        assertThat("There should exist an equal amount of TemporalFormats and examples. If you added a new TemporalFormat, did you forget to add an example?",
                TemporalFormat.values().length, is(TemporalFormat.examples().size()));

        for (TemporalFormat format : TemporalFormat.values()) {
            assertThat("All TemporalFormats must have an example", format.exampleString(), is(notNullValue()));
        }
    }

}
