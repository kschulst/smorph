package com.github.kschulst.smorph.base;

import com.github.kschulst.smorph.converters.nullsafe.ToXMLGregorianCalendar;
import org.joda.time.DateTime;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class XMLGregorianCalendarsTest {
    private static final DateTime dateTimeWithTime = new DateTime(2007, 2, 23, 13, 37, 42, 19);
    private static final DateTime dateTimeWithoutTime = new DateTime("2007-02-23");
    private static final XMLGregorianCalendar calWithTime = ToXMLGregorianCalendar.asDateTime(dateTimeWithTime);
    private static final XMLGregorianCalendar calWithoutTime = ToXMLGregorianCalendar.asDateOnly(dateTimeWithoutTime);

    @Test
    public void isDateTime() throws Exception {
        assertThat(XMLGregorianCalendars.isDateTime(null), is(false));
        assertThat(XMLGregorianCalendars.isDateTime(calWithTime), is(true));
        assertThat(XMLGregorianCalendars.isDateTime(calWithoutTime), is(false));
    }

    @Test
    public void isDateOnly() throws Exception {
        assertThat(XMLGregorianCalendars.isDateOnly(null), is(false));
        assertThat(XMLGregorianCalendars.isDateOnly(calWithTime), is(false));
        assertThat(XMLGregorianCalendars.isDateOnly(calWithoutTime), is(true));
    }

}
