package com.github.kschulst.smorph.converters.nullsafe;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.github.kschulst.smorph.base.TemporalFormat.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ToIntervalTest {
    private static final Locale LOCALE = new Locale("nb", "NO");
    private static final DateTime START_WITH_TIME = new DateTime(2007, 2, 23, 21, 11, 13, 0);
    private static final DateTime END_WITH_TIME = new DateTime(2008, 9, 18, 8, 7, 37, 0);
    private static final Interval INTERVAL_WITH_TIME = new Interval(START_WITH_TIME, END_WITH_TIME);
    private static final DateTime START_WITHOUT_TIME = new DateTime("2007-02-23");
    private static final DateTime END_WITHOUT_TIME = new DateTime("2008-09-18");
    private static final Interval INTERVAL_WITHOUT_TIME = new Interval(START_WITHOUT_TIME, END_WITHOUT_TIME);

    @Test
    public void fromString() throws Exception {
        assertThat(ToInterval.from("    23.02.2007 - 18.09.2008    ", DD_MM_YYYY, " - "), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from("2007-02-23-2008-09-18", ISO8601DateOnly, "-"), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from("23.02.2007 21:11:13 - 18.09.2008 08:07:37", DD_MM_YYYY_HH_MM_SS, " - "), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from("2007022321111320080918080737", YYYYMMDDHHMMSS, ""), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from(null, DD_MM_YYYY, " - "), is(nullValue()));
        assertThat(ToInterval.from("", DD_MM_YYYY, " - "), is(nullValue()));
        assertThat(ToInterval.from("bogus", DD_MM_YYYY, " - "), is(nullValue()));
        assertThat(ToInterval.convert("bogus", DD_MM_YYYY, " - ").withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

    @Test
    public void fromStartEndString() throws Exception {
        assertThat(ToInterval.from("      23.02.2007      ", "    18.09.2008    ", DD_MM_YYYY), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from("2007-02-23", "2008-09-18", ISO8601DateOnly), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from("23.02.2007 21:11:13", "18.09.2008 08:07:37", DD_MM_YYYY_HH_MM_SS), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from("20070223211113", "20080918080737", YYYYMMDDHHMMSS), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from(null, null, DD_MM_YYYY), is(nullValue()));
        assertThat(ToInterval.from("", "", DD_MM_YYYY), is(nullValue()));
        assertThat(ToInterval.from("bogus", "20080918080737", YYYYMMDDHHMMSS), is(nullValue()));
        assertThat(ToInterval.convert("bogus", null, DD_MM_YYYY).withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

    @Test
    public void fromDateTime() throws Exception {
        assertThat(ToInterval.from(START_WITH_TIME, END_WITH_TIME), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from(START_WITHOUT_TIME, END_WITHOUT_TIME), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from((DateTime) null, (DateTime) null), is(nullValue()));
        assertThat(ToInterval.convert((DateTime)null, (DateTime)null).withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

    @Test
    public void fromLocalDate() throws Exception {
        LocalDate start = START_WITH_TIME.toLocalDate();
        LocalDate end = END_WITH_TIME.toLocalDate();

        assertThat(ToInterval.from(start, end), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from(start, end), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from((LocalDate)null, (LocalDate)null), is(nullValue()));
        assertThat(ToInterval.convert((LocalDate)null, (LocalDate)null).withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

    @Test
    public void fromDate() throws Exception {
        Date startWithTime = START_WITH_TIME.toDate();
        Date endWithTime = END_WITH_TIME.toDate();
        Date startWithoutTime = START_WITHOUT_TIME.toDate();
        Date endWithoutTime = END_WITHOUT_TIME.toDate();

        assertThat(ToInterval.from(startWithTime, endWithTime), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from(startWithoutTime, endWithoutTime), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from((Date) null, (Date) null), is(nullValue()));
        assertThat(ToInterval.convert((Date)null, (Date)null).withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

    @Test
    public void fromCalendar() throws Exception {
        Calendar startWithTime = START_WITH_TIME.toCalendar(LOCALE);
        Calendar endWithTime = END_WITH_TIME.toCalendar(LOCALE);
        Calendar startWithoutTime = START_WITHOUT_TIME.toCalendar(LOCALE);
        Calendar endWithoutTime = END_WITHOUT_TIME.toCalendar(LOCALE);

        assertThat(ToInterval.from(startWithTime, endWithTime), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from(startWithoutTime, endWithoutTime), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from((Calendar) null, (Calendar) null), is(nullValue()));
        assertThat(ToInterval.convert((Calendar)null, (Calendar)null).withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

    @Test
    public void fromXMLGregorianCalendar() throws Exception {
        XMLGregorianCalendar startWithTime = ToXMLGregorianCalendar.asDateTime(START_WITH_TIME);
        XMLGregorianCalendar endWithTime = ToXMLGregorianCalendar.asDateTime(END_WITH_TIME);
        XMLGregorianCalendar startWithoutTime = ToXMLGregorianCalendar.asDateOnly(START_WITH_TIME);
        XMLGregorianCalendar endWithoutTime = ToXMLGregorianCalendar.asDateOnly(END_WITH_TIME);

        assertThat(ToInterval.from(startWithTime, endWithTime), is(INTERVAL_WITH_TIME));
        assertThat(ToInterval.from(startWithoutTime, endWithoutTime), is(INTERVAL_WITHOUT_TIME));
        assertThat(ToInterval.from((Calendar) null, (Calendar) null), is(nullValue()));
        assertThat(ToInterval.convert((Calendar)null, (Calendar)null).withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

    @Test
    public void forYear() throws Exception {
        assertThat(ToInterval.forYear("2012"), is(new Interval(new DateTime("2012-01-01"), new DateTime("2012-12-31"))));
        assertThat(ToInterval.forYear(null), is(nullValue()));
        assertThat(ToInterval.forYear("bogus"), is(nullValue()));
        assertThat(ToInterval.forYear(""), is(nullValue()));
        assertThat(ToInterval.convertForYear(null).withDefaultValue(INTERVAL_WITH_TIME), is(INTERVAL_WITH_TIME));
    }

}
