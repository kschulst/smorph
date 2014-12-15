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

public class ToLocalDateTest {
    private static final LocalDate LOCAL_DATE = new LocalDate("2007-02-23");
    private static final DateTime DATE_TIME = new DateTime("2007-02-23T13:37:42");

    @Test
    public void fromDateTime() throws Exception {
        assertThat(ToLocalDate.from((DateTime) null), is(nullValue()));
        assertThat(ToLocalDate.from(DATE_TIME), is(LOCAL_DATE));
        assertThat(ToLocalDate.convert((DateTime) null).withDefaultValue(LOCAL_DATE), is(LOCAL_DATE));
    }

    @Test
    public void fromDate() throws Exception {
        Date d = ToDate.from(DATE_TIME);
        assertThat(ToLocalDate.from((Date) null), is(nullValue()));
        assertThat(ToLocalDate.from(d), is(LOCAL_DATE));
        assertThat(ToLocalDate.convert((Date) null).withDefaultValue(LOCAL_DATE), is(LOCAL_DATE));
    }

    @Test
    public void fromCalendar() throws Exception {
        Calendar cal = ToCalendar.from(LOCAL_DATE);
        assertThat(ToLocalDate.from((Calendar) null), is(nullValue()));
        assertThat(ToLocalDate.from(cal), is(LOCAL_DATE));
        assertThat(ToLocalDate.convert((Calendar) null).withDefaultValue(LOCAL_DATE), is(LOCAL_DATE));
    }

    @Test
    public void fromXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar cal = ToXMLGregorianCalendar.asDateTime(DATE_TIME);
        assertThat(ToLocalDate.from((XMLGregorianCalendar) null), is(nullValue()));
        assertThat(ToLocalDate.from(cal), is(LOCAL_DATE));
        assertThat(ToLocalDate.convert((XMLGregorianCalendar) null).withDefaultValue(LOCAL_DATE), is(LOCAL_DATE));
    }

    @Test
    public void fromJAXBXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar cal = ToXMLGregorianCalendar.asDateTime(DATE_TIME);
        JAXBElement jaxbElement = Fixtures.jaxbElement(XMLGregorianCalendar.class, cal);
        assertThat(ToLocalDate.fromXMLGregorianCalendar(null), is(nullValue()));
        assertThat(ToLocalDate.fromXMLGregorianCalendar(jaxbElement), is(LOCAL_DATE));
    }

    @Test
    public void fromJAXBCalendar() throws Exception {
        Calendar cal = ToCalendar.from(LOCAL_DATE);
        JAXBElement jaxbElement = Fixtures.jaxbElement(Calendar.class, cal);
        assertThat(ToLocalDate.fromCalendar(null), is(nullValue()));
        assertThat(ToLocalDate.fromCalendar(jaxbElement), is(LOCAL_DATE));
    }

    @Test
    public void fromJAXBDate() throws Exception {
        Date date = ToDate.from(LOCAL_DATE);
        JAXBElement jaxbElement = Fixtures.jaxbElement(Date.class, date);
        assertThat(ToLocalDate.fromDate(null), is(nullValue()));
        assertThat(ToLocalDate.fromDate(jaxbElement), is(LOCAL_DATE));
    }

    @Test
    public void fromString() throws Exception {
        assertThat(ToLocalDate.from(null, DD_MM_YYYY), is(nullValue()));
        assertThat(ToLocalDate.from("bogus", DD_MM_YYYY), is(nullValue()));
        assertThat(ToLocalDate.from("", DD_MM_YYYY), is(nullValue()));

        assertThat(ToLocalDate.from("23.02.2007", DD_MM_YYYY), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("23.02.07", DD_MM_YY), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("23.02.2007 13:37", DD_MM_YYYY_HH_MM), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("23.02.2007 13:37:42", DD_MM_YYYY_HH_MM_SS), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("23.02.2007 13:37:42.009", DD_MM_YYYY_HH_MM_SS_SSS), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("20070223", YYYYMMDD), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("200702231337", YYYYMMDDHHMM), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("20070223133742", YYYYMMDDHHMMSS), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("2007.02.23", YYYY_MM_DD), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("2007.02.23 13:37", YYYY_MM_DD_HH_MM), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("2007.02.23 13:37:42", YYYY_MM_DD_HH_MM_SS), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("2007.02.23 13:37:42.009", YYYY_MM_DD_HH_MM_SS_SSS), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("2007-02-23", ISO8601DateOnly), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("2007-02-23T13:37:42+01:00", ISO8601DateTime), is(LOCAL_DATE));
        assertThat(ToLocalDate.from("2007-02-23T13:37:42.009+01:00", ISO8601DateTimeWithMillis), is(LOCAL_DATE));

        assertThat(ToLocalDate.convert(null, DD_MM_YYYY).withDefaultValue(LOCAL_DATE), is(LOCAL_DATE));
        assertThat(ToLocalDate.convert("bogus", DD_MM_YYYY).withDefaultValue(LOCAL_DATE), is(LOCAL_DATE));
    }

    @Test
    public void forStartOfYear() throws Exception {
        assertThat(ToLocalDate.forStartOfYear("2012"), is(new LocalDate("2012-01-01")));
        assertThat(ToLocalDate.forStartOfYear("bogus"), is(nullValue()));
        assertThat(ToLocalDate.forStartOfYear(""), is(nullValue()));
        assertThat(ToLocalDate.forStartOfYear(null), is(nullValue()));
    }

    @Test
    public void forEndOfYear() throws Exception {
        assertThat(ToLocalDate.forEndOfYear("2012"), is(new LocalDate("2012-12-31")));
        assertThat(ToLocalDate.forEndOfYear("bogus"), is(nullValue()));
        assertThat(ToLocalDate.forEndOfYear(""), is(nullValue()));
        assertThat(ToLocalDate.forEndOfYear(null), is(nullValue()));
    }

}