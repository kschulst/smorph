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

public class ToDateTest {
    private static final DateTime DATE_TIME_WITH_TIME = new DateTime(2007, 2, 23, 13, 37, 42, 19);
    private static final DateTime DATE_TIME_WITHOUT_TIME = new DateTime("2007-02-23");
    private static final LocalDate LOCAL_DATE = new LocalDate("2007-02-23");
    private static final Date DATE_WITH_TIME = DATE_TIME_WITH_TIME.toDate();
    private static final Date DATE_WITHOUT_TIME = DATE_TIME_WITHOUT_TIME.toDate();

    @Test
    public void fromLocalDate() throws Exception {
        assertThat(ToDate.from((LocalDate) null), is(nullValue()));
        assertThat(ToDate.from(LOCAL_DATE), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.convert((LocalDate) null).withDefaultValue(DATE_WITH_TIME), is(DATE_WITH_TIME));
    }

    @Test
    public void fromDateTime() throws Exception {
        assertThat(ToDate.from((DateTime) null), is(nullValue()));
        assertThat(ToDate.from(DATE_TIME_WITH_TIME), is(DATE_WITH_TIME));
        assertThat(ToDate.from(DATE_TIME_WITHOUT_TIME), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.convert((DateTime) null).withDefaultValue(DATE_WITHOUT_TIME), is(DATE_WITHOUT_TIME));
    }

    @Test
    public void fromCalendar() throws Exception {
        assertThat(ToDate.from((Calendar) null), is(nullValue()));
        assertThat(ToDate.from(ToCalendar.from(DATE_TIME_WITH_TIME)), is(DATE_WITH_TIME));
        assertThat(ToDate.from(ToCalendar.from(DATE_TIME_WITHOUT_TIME)), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.convert((Calendar) null).withDefaultValue(DATE_WITH_TIME), is(DATE_WITH_TIME));
    }

    @Test
    public void fromXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITH_TIME);
        XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITHOUT_TIME);
        assertThat(ToDate.from((XMLGregorianCalendar) null), is(nullValue()));
        assertThat(ToDate.from(calWithTime), is(DATE_WITH_TIME));
        assertThat(ToDate.from(calWithoutTime), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.convert((XMLGregorianCalendar) null).withDefaultValue(DATE_WITH_TIME), is(DATE_WITH_TIME));
    }

    @Test
    public void fromJAXBXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITH_TIME);
        XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITHOUT_TIME);
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithTime);
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithoutTime);

        assertThat(ToDate.fromXMLGregorianCalendar(null), is(nullValue()));
        assertThat(ToDate.fromXMLGregorianCalendar(jaxbElementWithTime), is(DATE_WITH_TIME));
        assertThat(ToDate.fromXMLGregorianCalendar(jaxbElementWithoutTime), is(DATE_WITHOUT_TIME));
    }

    @Test
    public void fromJAXBCalendar() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITH_TIME));
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITHOUT_TIME));
        assertThat(ToDate.fromCalendar(null), is(nullValue()));
        assertThat(ToDate.fromCalendar(jaxbElementWithTime), is(DATE_WITH_TIME));
        assertThat(ToDate.fromCalendar(jaxbElementWithoutTime), is(DATE_WITHOUT_TIME));
    }

    @Test
    public void fromJAXBDate() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Date.class, DATE_WITH_TIME);
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Date.class, DATE_WITHOUT_TIME);
        assertThat(ToDate.from((JAXBElement) null), is(nullValue()));
        assertThat(ToDate.from(jaxbElementWithTime), is(DATE_WITH_TIME));
        assertThat(ToDate.from(jaxbElementWithoutTime), is(DATE_WITHOUT_TIME));
    }

    @Test
    public void fromString() throws Exception {
        assertThat(ToDate.from(null, DD_MM_YYYY), is(nullValue()));
        assertThat(ToDate.from("bogus", DD_MM_YYYY), is(nullValue()));
        assertThat(ToDate.from("", DD_MM_YYYY), is(nullValue()));

        assertThat(ToDate.from("23.02.2007", DD_MM_YYYY), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.from("23.02.07", DD_MM_YY), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.from("23.02.2007 13:37", DD_MM_YYYY_HH_MM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0).toDate()));
        assertThat(ToDate.from("23.02.2007 13:37:42", DD_MM_YYYY_HH_MM_SS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toDate()));
        assertThat(ToDate.from("23.02.2007 13:37:42.009", DD_MM_YYYY_HH_MM_SS_SSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 9).toDate()));
        assertThat(ToDate.from("20070223", YYYYMMDD), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.from("200702231337", YYYYMMDDHHMM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0).toDate()));
        assertThat(ToDate.from("20070223133742", YYYYMMDDHHMMSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toDate()));
        assertThat(ToDate.from("2007.02.23", YYYY_MM_DD), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.from("2007.02.23 13:37", YYYY_MM_DD_HH_MM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0).toDate()));
        assertThat(ToDate.from("2007.02.23 13:37:42", YYYY_MM_DD_HH_MM_SS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toDate()));
        assertThat(ToDate.from("2007.02.23 13:37:42.999", YYYY_MM_DD_HH_MM_SS_SSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 999).toDate()));
        assertThat(ToDate.from("2007-02-23", ISO8601DateOnly), is(DATE_WITHOUT_TIME));
        assertThat(ToDate.from("2007-02-23T13:37:42+01:00", ISO8601DateTime), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toDate()));
        assertThat(ToDate.from("2007-02-23T13:37:42.089+01:00", ISO8601DateTimeWithMillis), is(new DateTime(2007, 2, 23, 13, 37, 42, 89).toDate()));

        assertThat(ToDate.convert(null, DD_MM_YYYY).withDefaultValue(DATE_WITH_TIME), is(DATE_WITH_TIME));
        assertThat(ToDate.convert("", DD_MM_YYYY).withNullAsDefaultValue(), is(nullValue()));
        assertThat(ToDate.convert("bogus", DD_MM_YYYY).withDefaultValue(DATE_WITH_TIME), is(DATE_WITH_TIME));
    }

    @Test
    public void forStartOfYear() throws Exception {
        assertThat(ToDate.forStartOfYear("2012"), is(new DateTime("2012-01-01").toDate()));
        assertThat(ToDate.forStartOfYear("bogus"), is(nullValue()));
        assertThat(ToDate.forStartOfYear(""), is(nullValue()));
        assertThat(ToDate.forStartOfYear(null), is(nullValue()));
    }
    @Test
    public void forEndOfYear() throws Exception {
        assertThat(ToDate.forEndOfYear("2012"), is(new DateTime("2012-12-31").toDate()));
        assertThat(ToDate.forEndOfYear("bogus"), is(nullValue()));
        assertThat(ToDate.forEndOfYear(""), is(nullValue()));
        assertThat(ToDate.forEndOfYear(null), is(nullValue()));
    }

}