package com.github.kschulst.smorph.converters;

import com.google.common.base.Function;
import com.github.kschulst.smorph.base.TemporalFormat;
import org.joda.time.LocalDate;
import org.junit.Test;

import static com.github.kschulst.smorph.base.TemporalFormat.DD_MM_YYYY;
import static com.github.kschulst.smorph.converters.Converter.transform;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ConverterTest {

    private static final LocalDate LOCAL_DATE = new LocalDate("2007-02-23");

    private static <X> Function noOperationFunction() {
        return new Function<Conversion<X, X>, X>() {
            public X apply(Conversion<X, X> conversion) {
                return conversion.fromValue();
            }
        };
    }

    private static final Function fromStringToLocalDate = new Function<Conversion<String, LocalDate>, LocalDate>() {
        public LocalDate apply(Conversion<String, LocalDate> conversion) {
            return new LocalDate(conversion.fromValue());
        }
    };

    private static Function fromLocalDateToString(final TemporalFormat format) {
        return new Function<Conversion<LocalDate, String>, String>() {
            public String apply(Conversion<LocalDate, String> conversion) {

                // Some custom rule
                if (conversion.fromValue().isBefore(new LocalDate("2000-01-01"))) {
                    return conversion.resolveDefault("Dates before y2k not supported. Dateformat: " + format);
                }

                return format.getFormatter().print(conversion.fromValue());
            }
        };
    }

    // Static function
    private static Converter<String, LocalDate> toLocalDate(String fromValue) {
        return transform(fromValue, fromStringToLocalDate);
    }

    // Dynamic function
    private static Converter<LocalDate, String> toDateString(LocalDate fromValue, TemporalFormat format) {
        return transform(fromValue, fromLocalDateToString(format));
    }

    private static <X> Converter<X, X> noOperation(X fromValue) {
        return transform(fromValue, noOperationFunction());
    }

    @Test
    public void testConvert_withDefaultValue_usingStaticFunction_() throws Exception {
        assertThat(toLocalDate("2007-02-23").withDefaultValue(null), is(LOCAL_DATE));
        assertThat(toLocalDate(null).withNullAsDefaultValue(), is(nullValue()));
        assertThat(toLocalDate("Bogus").withNullAsDefaultValue(), is(nullValue()));
        assertThat(toLocalDate(null).withDefaultValue(LOCAL_DATE), is(LOCAL_DATE));
    }

    @Test
    public void testConvert_withDefaultValue_usingDynamicFunction() throws Exception {
        assertThat(toDateString(LOCAL_DATE, DD_MM_YYYY).withDefaultValue(null), is("23.02.2007"));
        assertThat(toDateString(null, null).withNullAsDefaultValue(), is(nullValue()));
        assertThat(toDateString(null, DD_MM_YYYY).withDefaultValue("default"), is("default"));
        assertThat(toDateString(null, null).withDefaultValue("default"), is("default"));
    }

    @Test
    public void testConvert_orThrowException_usingStaticFunction() throws Exception {
        try {
            toLocalDate(null).orThrowException("Custom error message", "birthDate");
            fail("Expected conversion to throw exception");
        }
        catch (ConversionException e) {
            assertThat(e.getConversionReference(), is("birthDate"));
            assertThat(e.getMessage(), is("birthDate - Error converting from 'null'. Custom error message. Null is not allowed"));
            assertThat(e.getCause(), is(nullValue()));
        }

        try {
            toLocalDate("bogus").orThrowException("birthDate");
            fail("Expected conversion to throw exception due to invalid format");
        }
        catch (ConversionException e) {
            assertThat(e.getConversionReference(), is("birthDate"));
            assertThat(e.getMessage(), is("birthDate - Error converting from 'bogus'. Invalid format: \"bogus\""));
            assertThat(e.getCause(), is(instanceOf(IllegalArgumentException.class)));
        }

        try {
            toLocalDate("bogus").orThrowException();
            fail("Expected conversion to throw exception due to invalid format");
        }
        catch (ConversionException e) {
            assertThat(e.getConversionReference(), is(nullValue()));
            assertThat(e.getMessage(), is("Error converting from 'bogus'. Invalid format: \"bogus\""));
            assertThat(e.getCause(), is(instanceOf(IllegalArgumentException.class)));
        }
    }

    @Test
    public void testConvert_orThrowException_usingDynamicFunction() throws Exception {
        try {
            toDateString(new LocalDate("1999-12-31"), DD_MM_YYYY).orThrowException("Custom error message", "birthDateAsString");
            fail("Expected conversion to throw exception");
        }
        catch (ConversionException e) {
            assertThat(e.getConversionReference(), is("birthDateAsString"));
            assertThat(e.getMessage(), is("birthDateAsString - Error converting from '1999-12-31'. Custom error message. Dates before y2k not supported. Dateformat: DD_MM_YYYY"));
        }

        try {
            toDateString(new LocalDate("1999-12-31"), DD_MM_YYYY).orThrowException("birthDateAsString");
            fail("Expected conversion to throw exception");
        }
        catch (ConversionException e) {
            assertThat(e.getConversionReference(), is("birthDateAsString"));
            assertThat(e.getMessage(), is("birthDateAsString - Error converting from '1999-12-31'. Dates before y2k not supported. Dateformat: DD_MM_YYYY"));
        }

        try {
            toDateString(new LocalDate("1999-12-31"), DD_MM_YYYY).orThrowException();
            fail("Expected conversion to throw exception");
        }
        catch (ConversionException e) {
            assertThat(e.getConversionReference(), is(nullValue()));
            assertThat(e.getMessage(), is("Error converting from '1999-12-31'. Dates before y2k not supported. Dateformat: DD_MM_YYYY"));
        }

    }

    @Test
    public void testConvert_trimInput() {
        assertThat(noOperation("  \t  \n   a string with leading and trailing whitespace \t \n     ").trimInput().withNullAsDefaultValue(), is("a string with leading and trailing whitespace"));
        assertThat(noOperation("  \t  \n   a string with leading and trailing whitespace \t \n     ").withNullAsDefaultValue(), is("  \t  \n   a string with leading and trailing whitespace \t \n     "));
    }

    @Test
    public void testConvert_allowEmptyInput() {
        assertThat(noOperation("").withNullAsDefaultValue(), is(nullValue()));
        assertThat(noOperation("").allowEmptyInput().withNullAsDefaultValue(), is(""));
        assertThat(noOperation("    ").trimInput().withNullAsDefaultValue(), is(nullValue()));
        assertThat(noOperation("    ").trimInput().allowEmptyInput().withNullAsDefaultValue(), is(""));

        try {
            noOperation("").orThrowException("someField");
            fail("Expected conversion to throw exception");
        }
        catch (ConversionException e) {
            assertThat(e.getMessage(), is("someField - Error converting from ''. Empty strings not allowed"));
        }
    }

}
