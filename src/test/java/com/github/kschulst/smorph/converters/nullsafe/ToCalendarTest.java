package com.github.kschulst.smorph.converters.nullsafe;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.github.kschulst.smorph.base.TemporalFormat.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ToCalendarTest {
    private static final Locale LOCALE = new Locale("nb", "NO");
    private static final DateTime DATE_TIME_WITH_TIME = new DateTime(2007, 2, 23, 13, 37, 42, 19);
    private static final DateTime DATE_TIME_WITHOUT_TIME = new DateTime("2007-02-23");
    private static final LocalDate LOCAL_DATE = new LocalDate("2007-02-23");
    private static final Date DATE_WITH_TIME = DATE_TIME_WITH_TIME.toDate();
    private static final Date DATE_WITHOUT_TIME = DATE_TIME_WITHOUT_TIME.toDate();
    private static final Calendar CAL_WITH_TIME = DATE_TIME_WITH_TIME.toCalendar(LOCALE);
    private static final Calendar CAL_WITHOUT_TIME = DATE_TIME_WITHOUT_TIME.toCalendar(LOCALE);

    @Test
    public void fromLocalDate() throws Exception {
        assertThat(ToCalendar.from((LocalDate) null), is(nullValue()));
        assertThat(ToCalendar.from(LOCAL_DATE), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.convert((LocalDate) null).withDefaultValue(CAL_WITH_TIME), is(CAL_WITH_TIME));
    }

    @Test
    public void fromDateTime() throws Exception {
        assertThat(ToCalendar.from((DateTime) null), is(nullValue()));
        assertThat(ToCalendar.from(DATE_TIME_WITH_TIME), is(CAL_WITH_TIME));
        assertThat(ToCalendar.from(DATE_TIME_WITHOUT_TIME), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.convert((DateTime) null).withDefaultValue(CAL_WITH_TIME), is(CAL_WITH_TIME));
    }

    @Test
    public void fromDate() throws Exception {
        assertThat(ToCalendar.from((Date) null), is(nullValue()));
        assertThat(ToCalendar.from(DATE_WITH_TIME), is(CAL_WITH_TIME));
        assertThat(ToCalendar.from(DATE_WITHOUT_TIME), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.convert((Date) null).withDefaultValue(CAL_WITHOUT_TIME), is(CAL_WITHOUT_TIME));
    }

    @Test
    public void fromXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITH_TIME);
        XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITHOUT_TIME);
        assertThat(ToCalendar.from((XMLGregorianCalendar) null), is(nullValue()));
        assertThat(ToCalendar.from(calWithTime), is(CAL_WITH_TIME));
        assertThat(ToCalendar.from(calWithoutTime), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.convert((XMLGregorianCalendar) null).withDefaultValue(CAL_WITH_TIME), is(CAL_WITH_TIME));
    }

    @Test
    public void fromJAXBXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateTime(DATE_TIME_WITH_TIME);
        XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(DATE_TIME_WITHOUT_TIME);
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithTime);
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(XMLGregorianCalendar.class, calWithoutTime);

        assertThat(ToCalendar.fromXMLGregorianCalendar(null), is(nullValue()));
        assertThat(ToCalendar.fromXMLGregorianCalendar(jaxbElementWithTime), is(CAL_WITH_TIME));
        assertThat(ToCalendar.fromXMLGregorianCalendar(jaxbElementWithoutTime), is(CAL_WITHOUT_TIME));
    }

    @Test
    public void fromJAXBCalendar() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Calendar.class, CAL_WITH_TIME);
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Calendar.class, CAL_WITHOUT_TIME);
        assertThat(ToCalendar.from((JAXBElement) null), is(nullValue()));
        assertThat(ToCalendar.from(jaxbElementWithTime), is(CAL_WITH_TIME));
        assertThat(ToCalendar.from(jaxbElementWithoutTime), is(CAL_WITHOUT_TIME));
    }

    @Test
    public void fromJAXBDate() throws Exception {
        JAXBElement jaxbElementWithTime = Fixtures.jaxbElement(Date.class, DATE_WITH_TIME);
        JAXBElement jaxbElementWithoutTime = Fixtures.jaxbElement(Date.class, DATE_WITHOUT_TIME);
        assertThat(ToCalendar.fromDate((JAXBElement) null), is(nullValue()));
        assertThat(ToCalendar.fromDate(jaxbElementWithTime), is(CAL_WITH_TIME));
        assertThat(ToCalendar.fromDate(jaxbElementWithoutTime), is(CAL_WITHOUT_TIME));
    }

    @Test
    public void fromString() throws Exception {
        assertThat(ToCalendar.from(null, DD_MM_YYYY), is(nullValue()));
        assertThat(ToCalendar.from("bogus", DD_MM_YYYY), is(nullValue()));
        assertThat(ToCalendar.from("", DD_MM_YYYY), is(nullValue()));

        assertThat(ToCalendar.from("23.02.2007", DD_MM_YYYY), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.from("23.02.07", DD_MM_YY), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.from("23.02.2007 13:37", DD_MM_YYYY_HH_MM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("23.02.2007 13:37:42", DD_MM_YYYY_HH_MM_SS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("23.02.2007 13:37:42.009", DD_MM_YYYY_HH_MM_SS_SSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 9).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("20070223", YYYYMMDD), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.from("200702231337", YYYYMMDDHHMM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("20070223133742", YYYYMMDDHHMMSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("2007.02.23", YYYY_MM_DD), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.from("2007.02.23 13:37", YYYY_MM_DD_HH_MM), is(new DateTime(2007, 2, 23, 13, 37, 0, 0).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("2007.02.23 13:37:42", YYYY_MM_DD_HH_MM_SS), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("2007.02.23 13:37:42.999", YYYY_MM_DD_HH_MM_SS_SSS), is(new DateTime(2007, 2, 23, 13, 37, 42, 999).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("2007-02-23", ISO8601DateOnly), is(CAL_WITHOUT_TIME));
        assertThat(ToCalendar.from("2007-02-23T13:37:42+01:00", ISO8601DateTime), is(new DateTime(2007, 2, 23, 13, 37, 42, 0).toCalendar(LOCALE)));
        assertThat(ToCalendar.from("2007-02-23T13:37:42.089+01:00", ISO8601DateTimeWithMillis), is(new DateTime(2007, 2, 23, 13, 37, 42, 89).toCalendar(LOCALE)));

        assertThat(ToCalendar.convert(null, DD_MM_YYYY).withDefaultValue(CAL_WITH_TIME), is(CAL_WITH_TIME));
        assertThat(ToCalendar.convert("", DD_MM_YYYY).withNullAsDefaultValue(), is(nullValue()));
        assertThat(ToCalendar.convert("bogus", DD_MM_YYYY).withDefaultValue(CAL_WITH_TIME), is(CAL_WITH_TIME));
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