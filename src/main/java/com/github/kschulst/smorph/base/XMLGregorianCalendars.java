package com.github.kschulst.smorph.base;

import com.google.common.collect.Range;

import javax.xml.datatype.XMLGregorianCalendar;

public final class XMLGregorianCalendars {

    private static final int HOURS_OF_DAY = 23;
    private static final int MINUTES_OF_HOUR = 59;
    private static final int SECONDS_OF_MINUTE = 59;

    private XMLGregorianCalendars() {}

    public static boolean isDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return false;
        }

        return Range.closed(0, HOURS_OF_DAY).contains(xmlGregorianCalendar.getHour()) &&
               Range.closed(0, MINUTES_OF_HOUR).contains(xmlGregorianCalendar.getMinute()) &&
               Range.closed(0, SECONDS_OF_MINUTE).contains(xmlGregorianCalendar.getSecond());
    }

    public static boolean isDateOnly(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return false;
        }

        return ! isDateTime(xmlGregorianCalendar);
    }
}
