package com.github.kschulst.smorph.converters.formatters;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.github.kschulst.smorph.base.TemporalFormat.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ToDateStringTest {
    private final static Locale NORWEGIAN_LOCALE = new Locale("nb", "NO");

    @Test
    public void fromLocalDate() throws Exception {
        LocalDate d = new LocalDate("2007-02-23");
        assertThat(ToDateString.from((LocalDate) null, DD_MM_YYYY), is(nullValue()));

        assertThat(ToDateString.from(d, DD_MM_YYYY), is("23.02.2007"));
        assertThat(ToDateString.from(d, DD_MM_YY), is("23.02.07"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM), is("23.02.2007 00:00"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM_SS), is("23.02.2007 00:00:00"));
        assertThat(ToDateString.from(d, YYYYMMDD), is("20070223"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMM), is("200702230000"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMMSS), is("20070223000000"));
        assertThat(ToDateString.from(d, YYYY_MM_DD), is("2007.02.23"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM), is("2007.02.23 00:00"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM_SS), is("2007.02.23 00:00:00"));
        assertThat(ToDateString.from(d, ISO8601DateOnly), is("2007-02-23"));
    }

    @Test
    public void fromDateTime() throws Exception {
        DateTime d = new DateTime("2007-02-23T13:37:42");
        assertThat(ToDateString.from((DateTime) null, DD_MM_YYYY), is(nullValue()));

        assertThat(ToDateString.from(d, DD_MM_YYYY), is("23.02.2007"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM), is("23.02.2007 13:37"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM_SS), is("23.02.2007 13:37:42"));
        assertThat(ToDateString.from(d, YYYYMMDD), is("20070223"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMM), is("200702231337"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMMSS), is("20070223133742"));
        assertThat(ToDateString.from(d, YYYY_MM_DD), is("2007.02.23"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM), is("2007.02.23 13:37"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM_SS), is("2007.02.23 13:37:42"));
        assertThat(ToDateString.from(d, ISO8601DateOnly), is("2007-02-23"));
    }

    @Test
    public void fromDate() throws Exception {
        Date d = new DateTime("2007-02-23T13:37:42").toDate();
        assertThat(ToDateString.from((Date) null, DD_MM_YYYY), is(nullValue()));

        assertThat(ToDateString.from(d, DD_MM_YYYY), is("23.02.2007"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM), is("23.02.2007 13:37"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM_SS), is("23.02.2007 13:37:42"));
        assertThat(ToDateString.from(d, YYYYMMDD), is("20070223"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMM), is("200702231337"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMMSS), is("20070223133742"));
        assertThat(ToDateString.from(d, YYYY_MM_DD), is("2007.02.23"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM), is("2007.02.23 13:37"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM_SS), is("2007.02.23 13:37:42"));
        assertThat(ToDateString.from(d, ISO8601DateOnly), is("2007-02-23"));

        d = new LocalDate("2007-02-23").toDate();
        assertThat(ToDateString.from(d, DD_MM_YYYY), is("23.02.2007"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM), is("23.02.2007 00:00"));
        assertThat(ToDateString.from(d, DD_MM_YYYY_HH_MM_SS), is("23.02.2007 00:00:00"));
        assertThat(ToDateString.from(d, YYYYMMDD), is("20070223"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMM), is("200702230000"));
        assertThat(ToDateString.from(d, YYYYMMDDHHMMSS), is("20070223000000"));
        assertThat(ToDateString.from(d, YYYY_MM_DD), is("2007.02.23"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM), is("2007.02.23 00:00"));
        assertThat(ToDateString.from(d, YYYY_MM_DD_HH_MM_SS), is("2007.02.23 00:00:00"));
        assertThat(ToDateString.from(d, ISO8601DateOnly), is("2007-02-23"));
    }

    @Test
    public void fromCalendar() throws Exception {
        Calendar cal = new DateTime("2007-02-23T13:37:42").toCalendar(NORWEGIAN_LOCALE);
        assertThat(ToDateString.from((Date) null, DD_MM_YYYY), is(nullValue()));

        assertThat(ToDateString.from(cal, DD_MM_YYYY), is("23.02.2007"));
        assertThat(ToDateString.from(cal, DD_MM_YYYY_HH_MM), is("23.02.2007 13:37"));
        assertThat(ToDateString.from(cal, DD_MM_YYYY_HH_MM_SS), is("23.02.2007 13:37:42"));
        assertThat(ToDateString.from(cal, YYYYMMDD), is("20070223"));
        assertThat(ToDateString.from(cal, YYYYMMDDHHMM), is("200702231337"));
        assertThat(ToDateString.from(cal, YYYYMMDDHHMMSS), is("20070223133742"));
        assertThat(ToDateString.from(cal, YYYY_MM_DD), is("2007.02.23"));
        assertThat(ToDateString.from(cal, YYYY_MM_DD_HH_MM), is("2007.02.23 13:37"));
        assertThat(ToDateString.from(cal, YYYY_MM_DD_HH_MM_SS), is("2007.02.23 13:37:42"));
        assertThat(ToDateString.from(cal, ISO8601DateOnly), is("2007-02-23"));

        cal = new LocalDate("2007-02-23").toDateTimeAtStartOfDay().toCalendar(NORWEGIAN_LOCALE);
        assertThat(ToDateString.from(cal, DD_MM_YYYY), is("23.02.2007"));
        assertThat(ToDateString.from(cal, DD_MM_YYYY_HH_MM), is("23.02.2007 00:00"));
        assertThat(ToDateString.from(cal, DD_MM_YYYY_HH_MM_SS), is("23.02.2007 00:00:00"));
        assertThat(ToDateString.from(cal, YYYYMMDD), is("20070223"));
        assertThat(ToDateString.from(cal, YYYYMMDDHHMM), is("200702230000"));
        assertThat(ToDateString.from(cal, YYYYMMDDHHMMSS), is("20070223000000"));
        assertThat(ToDateString.from(cal, YYYY_MM_DD), is("2007.02.23"));
        assertThat(ToDateString.from(cal, YYYY_MM_DD_HH_MM), is("2007.02.23 00:00"));
        assertThat(ToDateString.from(cal, YYYY_MM_DD_HH_MM_SS), is("2007.02.23 00:00:00"));
        assertThat(ToDateString.from(cal, ISO8601DateOnly), is("2007-02-23"));
    }

}
