package com.github.kschulst.smorph.converters.nullsafe;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static com.github.kschulst.smorph.base.TemporalFormat.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ToDateTimeTest {
    private static final DateTime DATE_TIME_WITH_TIME = new DateTime(2007, 2, 23, 13, 37, 42, 19);
    private static final DateTime DATE_TIME_WITHOUT_TIME = new DateTime("2007-02-23");
    private static final LocalDate LOCAL_DATE = new LocalDate("2007-02-23");

    @Test
    public void fromLocalDate() throws Exception {
        assertThat(ToDateTime.from((LocalDate) null), is(nullValue()));
        assertThat(ToDateTime.from(LOCAL_DATE), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.convert((LocalDate) null).withDefaultValue(DATE_TIME_WITH_TIME), is(DATE_TIME_WITH_TIME));
    }

    @Test
    public void fromDate() throws Exception {
        assertThat(ToDateTime.from((Date) null), is(nullValue()));
        assertThat(ToDateTime.from(ToDate.from(DATE_TIME_WITH_TIME)), is(DATE_TIME_WITH_TIME));
        assertThat(ToDateTime.from(ToDate.from(DATE_TIME_WITHOUT_TIME)), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.convert((Date) null).withDefaultValue(DATE_TIME_WITHOUT_TIME), is(DATE_TIME_WITHOUT_TIME));
    }

    @Test
    public void fromCalendar() throws Exception {
        assertThat(ToDateTime.from((Calendar) null), is(nullValue()));
        assertThat(ToDateTime.from(ToCalendar.from(DATE_TIME_WITH_TIME)), is(DATE_TIME_WITH_TIME));
        assertThat(ToDateTime.from(ToCalendar.from(DATE_TIME_WITHOUT_TIME)), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.convert((Calendar) null).withDefaultValue(DATE_TIME_WITH_TIME), is(DATE_TIME_WITH_TIME));
    }

    @Test
    public void fromXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITH_TIME);
        XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITHOUT_TIME);
        assertThat(ToDateTime.from((XMLGregorianCalendar) null), is(nullValue()));
        assertThat(ToDateTime.from(calWithTime), is(DATE_TIME_WITH_TIME));
        assertThat(ToDateTime.from(calWithoutTime), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.convert((XMLGregorianCalendar) null).withDefaultValue(DATE_TIME_WITH_TIME), is(DATE_TIME_WITH_TIME));
    }

    @Test
    public void fromJAXBElementWithCalendar() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITH_TIME));
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.fromCalendar(null), is(nullValue()));
        assertThat(ToDateTime.fromCalendar(jaxbElementWithTime), is(DATE_TIME_WITH_TIME));
        assertThat(ToDateTime.fromCalendar(jaxbElementWithoutTime), is(DATE_TIME_WITHOUT_TIME));
    }

    @Test
    public void fromJAXBElementWithDate() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Date.class, ToDate.from(DATE_TIME_WITH_TIME));
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Date.class, ToDate.from(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.fromDate(null), is(nullValue()));
        assertThat(ToDateTime.fromDate(jaxbElementWithTime), is(DATE_TIME_WITH_TIME));
        assertThat(ToDateTime.fromDate(jaxbElementWithoutTime), is(DATE_TIME_WITHOUT_TIME));
    }

    @Test
    public void fromJAXBElementWithXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITH_TIME);
        XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITHOUT_TIME);
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithTime);
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithoutTime);

        assertThat(ToDateTime.fromXMLGregorianCalendar(null), is(nullValue()));
        assertThat(ToDateTime.fromXMLGregorianCalendar(jaxbElementWithTime), is(DATE_TIME_WITH_TIME));
        assertThat(ToDateTime.fromXMLGregorianCalendar(jaxbElementWithoutTime), is(DATE_TIME_WITHOUT_TIME));
    }

    @Test
    public void fromString() throws Exception {
        assertThat(ToDateTime.from(null, DD_MM_YYYY), is(nullValue()));
        assertThat(ToDateTime.from("bogus", DD_MM_YYYY), is(nullValue()));
        assertThat(ToDateTime.from("", DD_MM_YYYY), is(nullValue()));

        assertThat(ToDateTime.from("23.02.2007", DD_MM_YYYY), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.from("23.02.07", DD_MM_YY), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.from("23.02.2007 13:37", DD_MM_YYYY_HH_MM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0)));
        assertThat(ToDateTime.from("23.02.2007 13:37:42", DD_MM_YYYY_HH_MM_SS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0)));
        assertThat(ToDateTime.from("23.02.2007 13:37:42.009", DD_MM_YYYY_HH_MM_SS_SSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 9)));
        assertThat(ToDateTime.from("20070223", YYYYMMDD), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.from("200702231337", YYYYMMDDHHMM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0)));
        assertThat(ToDateTime.from("20070223133742", YYYYMMDDHHMMSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0)));
        assertThat(ToDateTime.from("2007.02.23", YYYY_MM_DD), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.from("2007.02.23 13:37", YYYY_MM_DD_HH_MM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0)));
        assertThat(ToDateTime.from("2007.02.23 13:37:42", YYYY_MM_DD_HH_MM_SS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0)));
        assertThat(ToDateTime.from("2007.02.23 13:37:42.089", YYYY_MM_DD_HH_MM_SS_SSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 89)));
        assertThat(ToDateTime.from("2007-02-23", ISO8601DateOnly), is(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDateTime.from("2007-02-23T13:37:42+01:00", ISO8601DateTime), is(new DateTime(2007, 2, 23, 13, 37, 42, 0)));
        assertThat(ToDateTime.from("2007-02-23T13:37:42.089+01:00", ISO8601DateTimeWithMillis), is(new DateTime(2007, 2, 23, 13, 37, 42, 89)));

        assertThat(ToDateTime.convert(null, DD_MM_YYYY).withDefaultValue(DATE_TIME_WITH_TIME), is(DATE_TIME_WITH_TIME));
        assertThat(ToDateTime.convert("", DD_MM_YYYY).withNullAsDefaultValue(), is(nullValue()));
        assertThat(ToDateTime.convert("bogus", DD_MM_YYYY).withDefaultValue(DATE_TIME_WITH_TIME), is(DATE_TIME_WITH_TIME));
    }

    @Test
    public void forStartOfYear() throws Exception {
        assertThat(ToDateTime.forStartOfYear("2012"), is(new DateTime("2012-01-01")));
        assertThat(ToDateTime.forStartOfYear("bogus"), is(nullValue()));
        assertThat(ToDateTime.forStartOfYear(""), is(nullValue()));
        assertThat(ToDateTime.forStartOfYear(null), is(nullValue()));
    }
    @Test
    public void forEndOfYear() throws Exception {
        assertThat(ToDateTime.forEndOfYear("2012"), is(new DateTime("2012-12-31")));
        assertThat(ToDateTime.forEndOfYear("bogus"), is(nullValue()));
        assertThat(ToDateTime.forEndOfYear(""), is(nullValue()));
        assertThat(ToDateTime.forEndOfYear(null), is(nullValue()));
    }
}