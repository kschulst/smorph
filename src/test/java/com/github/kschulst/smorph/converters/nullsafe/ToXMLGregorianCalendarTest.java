package com.github.kschulst.smorph.converters.nullsafe;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static com.github.kschulst.smorph.base.TemporalFormat.*;
import static com.github.kschulst.smorph.converters.nullsafe.ToXMLGregorianCalendar.withDateAndTime;
import static com.github.kschulst.smorph.converters.nullsafe.ToXMLGregorianCalendar.withDateOnly;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ToXMLGregorianCalendarTest {
    private static final DateTime DATE_TIME_WITH_TIME = new DateTime(2007, 2, 23, 13, 37, 42, 19);
    private static final DateTime DATE_TIME_WITHOUT_TIME = new DateTime("2007-02-23");
    private static final LocalDate LOCAL_DATE = new LocalDate("2007-02-23");
    private static final XMLGregorianCalendar DATETIME_XMLCAL_WITH_TIME = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITH_TIME);
    private static final XMLGregorianCalendar DATETIME_XMLCAL_WITHOUT_TIME = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITHOUT_TIME);
    private static final XMLGregorianCalendar DATEONLY_XMLCAL = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITH_TIME);

    @Test
    public void internalWithDateAndTime() throws Exception {
        assertThat(withDateAndTime(null), is(nullValue()));
        assertThat(withDateAndTime(-1, -1, -1, -1, -1, -1, -1), is(nullValue()));
    }

    @Test
    public void internalWithDate() throws Exception {
        assertThat(withDateOnly(-1, -1, -1), is(nullValue()));
    }

    @Test
    public void fromLocalDateAsDateOnly() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateOnly((LocalDate) null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateOnly(LOCAL_DATE), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly((LocalDate) null).withDefaultValue(DATEONLY_XMLCAL), is(DATEONLY_XMLCAL));
    }

    @Test
    public void fromDateAsDateOnly() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateOnly((Date) null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateOnly(ToDate.from(DATE_TIME_WITH_TIME)), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly(ToDate.from(DATE_TIME_WITHOUT_TIME)), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly((Date) null).withDefaultValue(DATEONLY_XMLCAL), is(DATEONLY_XMLCAL));
    }

    @Test
    public void fromCalendarAsDateOnly() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateOnly((Calendar) null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateOnly(ToCalendar.from(DATE_TIME_WITH_TIME)), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly(ToCalendar.from(DATE_TIME_WITHOUT_TIME)), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly((Calendar) null).withDefaultValue(DATEONLY_XMLCAL), is(DATEONLY_XMLCAL));
    }

    @Test
    public void fromJAXBCalendarAsDateOnly() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITH_TIME));
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.fromCalendarAsDateOnly(null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.fromCalendarAsDateOnly(jaxbElementWithTime), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.fromCalendarAsDateOnly(jaxbElementWithoutTime), is(DATEONLY_XMLCAL));
    }

    @Test
    public void fromJAXBDateAsDateOnly() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Date.class, ToDate.from(DATE_TIME_WITH_TIME));
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Date.class, ToDate.from(DATE_TIME_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.fromDateAsDateOnly(null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.fromDateAsDateOnly(jaxbElementWithTime), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.fromDateAsDateOnly(jaxbElementWithoutTime), is(DATEONLY_XMLCAL));
    }

    @Test
    public void fromStringAsDateOnly() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateOnly(null, DD_MM_YYYY), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateOnly("bogus", DD_MM_YYYY), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateOnly("", DD_MM_YYYY), is(nullValue()));

        assertThat(ToXMLGregorianCalendar.asDateOnly("23.02.2007", DD_MM_YYYY), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("23.02.07", DD_MM_YY), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("23.02.2007 13:37", DD_MM_YYYY_HH_MM), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("23.02.2007 13:37:42", DD_MM_YYYY_HH_MM_SS), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("20070223", YYYYMMDD), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("200702231337", YYYYMMDDHHMM), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("20070223133742", YYYYMMDDHHMMSS), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("2007.02.23", YYYY_MM_DD), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("2007.02.23 13:37", YYYY_MM_DD_HH_MM), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("2007.02.23 13:37:42", YYYY_MM_DD_HH_MM_SS), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.asDateOnly("2007-02-23", ISO8601DateOnly), is(DATEONLY_XMLCAL));

        assertThat(ToXMLGregorianCalendar.convertAsDateOnly(null, DD_MM_YYYY).withDefaultValue(DATEONLY_XMLCAL), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly("", DD_MM_YYYY).withNullAsDefaultValue(), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly("bogus", DD_MM_YYYY).withDefaultValue(DATEONLY_XMLCAL), is(DATEONLY_XMLCAL));
    }

    @Test
    public void fromLocalDateAsDateTime() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateTime((LocalDate) null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateTime(LOCAL_DATE), is(DATETIME_XMLCAL_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly((LocalDate) null).withDefaultValue(DATETIME_XMLCAL_WITH_TIME), is(DATETIME_XMLCAL_WITH_TIME));
    }

    @Test
    public void fromDateAsDateTime() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateTime((Date) null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateTime(ToDate.from(DATE_TIME_WITH_TIME)), is(DATETIME_XMLCAL_WITH_TIME));
        assertThat(ToXMLGregorianCalendar.asDateTime(ToDate.from(DATE_TIME_WITHOUT_TIME)), is(DATETIME_XMLCAL_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly((Date) null).withDefaultValue(DATETIME_XMLCAL_WITH_TIME), is(DATETIME_XMLCAL_WITH_TIME));
    }

    @Test
    public void fromCalendarAsDateTime() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateTime((Calendar) null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateTime(ToCalendar.from(DATE_TIME_WITH_TIME)), is(DATETIME_XMLCAL_WITH_TIME));
        assertThat(ToXMLGregorianCalendar.asDateTime(ToCalendar.from(DATE_TIME_WITHOUT_TIME)), is(DATETIME_XMLCAL_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly((Calendar) null).withDefaultValue(DATETIME_XMLCAL_WITH_TIME), is(DATETIME_XMLCAL_WITH_TIME));
    }

    @Test
    public void fromJAXBCalendarAsDateTime() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITH_TIME));
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Calendar.class, ToCalendar.from(DATE_TIME_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.fromCalendarAsDateTime(null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.fromCalendarAsDateTime(jaxbElementWithTime), is(DATETIME_XMLCAL_WITH_TIME));
        assertThat(ToXMLGregorianCalendar.fromCalendarAsDateTime(jaxbElementWithoutTime), is(DATETIME_XMLCAL_WITHOUT_TIME));
    }

    @Test
    public void fromJAXBDateAsDateTime() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Date.class, ToDate.from(DATE_TIME_WITH_TIME));
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Date.class, ToDate.from(DATE_TIME_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.fromDateAsDateTime(null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.fromDateAsDateTime(jaxbElementWithTime), is(DATETIME_XMLCAL_WITH_TIME));
        assertThat(ToXMLGregorianCalendar.fromDateAsDateTime(jaxbElementWithoutTime), is(DATETIME_XMLCAL_WITHOUT_TIME));
    }

    @Test
    public void fromStringAsDateTime() throws Exception {
        assertThat(ToXMLGregorianCalendar.asDateTime(null, DD_MM_YYYY), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateTime("bogus", DD_MM_YYYY), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.asDateTime("", DD_MM_YYYY), is(nullValue()));

        assertThat(ToXMLGregorianCalendar.asDateTime("23.02.2007", DD_MM_YYYY), is(DATETIME_XMLCAL_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.asDateTime("23.02.2007 13:37", DD_MM_YYYY_HH_MM), is(withDateAndTime(2007, 2, 23, 13, 37, 0, 0)));
        assertThat(ToXMLGregorianCalendar.asDateTime("23.02.2007 13:37:42", DD_MM_YYYY_HH_MM_SS), is(withDateAndTime(2007, 2, 23, 13, 37, 42, 0)));
        assertThat(ToXMLGregorianCalendar.asDateTime("20070223", YYYYMMDD), is(DATETIME_XMLCAL_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.asDateTime("200702231337", YYYYMMDDHHMM), is(withDateAndTime(2007, 2, 23, 13, 37, 0, 0)));
        assertThat(ToXMLGregorianCalendar.asDateTime("20070223133742", YYYYMMDDHHMMSS), is(withDateAndTime(2007, 2, 23, 13, 37, 42, 0)));
        assertThat(ToXMLGregorianCalendar.asDateTime("2007.02.23", YYYY_MM_DD), is(DATETIME_XMLCAL_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.asDateTime("2007.02.23 13:37", YYYY_MM_DD_HH_MM), is(withDateAndTime(2007, 2, 23, 13, 37, 0, 0)));
        assertThat(ToXMLGregorianCalendar.asDateTime("2007.02.23 13:37:42", YYYY_MM_DD_HH_MM_SS), is(withDateAndTime(2007, 2, 23, 13, 37, 42, 0)));
        assertThat(ToXMLGregorianCalendar.asDateTime("2007-02-23", ISO8601DateOnly), is(DATETIME_XMLCAL_WITHOUT_TIME));

        assertThat(ToXMLGregorianCalendar.convertAsDateOnly(null, DD_MM_YYYY).withDefaultValue(DATETIME_XMLCAL_WITHOUT_TIME), is(DATETIME_XMLCAL_WITHOUT_TIME));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly("", DD_MM_YYYY).withNullAsDefaultValue(), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.convertAsDateOnly("bogus", DD_MM_YYYY).withDefaultValue(DATETIME_XMLCAL_WITH_TIME), is(DATETIME_XMLCAL_WITH_TIME));
    }

    @Test
    public void fromJAXBElement() throws Exception {
        XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITH_TIME);
        XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITHOUT_TIME);
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithTime);
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithoutTime);

        assertThat(ToXMLGregorianCalendar.from(null), is(nullValue()));
        assertThat(ToXMLGregorianCalendar.from(jaxbElementWithTime), is(DATEONLY_XMLCAL));
        assertThat(ToXMLGregorianCalendar.from(jaxbElementWithoutTime), is(DATEONLY_XMLCAL));
    }

}
