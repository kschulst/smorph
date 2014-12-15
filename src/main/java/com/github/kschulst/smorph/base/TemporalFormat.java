package com.github.kschulst.smorph.base;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Map;

/**
 * Common norwegian date and time formats
 */
public enum TemporalFormat {

    /**
     * dd.MM.yyyy, like 23.02.2007
     */
    DD_MM_YYYY("dd.MM.yyyy", DateTimeAwareness.DATE_ONLY),

    /**
     * dd.MM.yy, like 23.02.07
     */
    DD_MM_YY("dd.MM.yy", DateTimeAwareness.DATE_ONLY),

    /**
     * dd.MM.yyyy HH:mm, like 23.02.2007 13:37
     */
    DD_MM_YYYY_HH_MM("dd.MM.yyyy HH:mm", DateTimeAwareness.DATE_AND_TIME),

    /**
     * dd.MM.yyyy HH:mm:ss, like 23.02.2007 13:37:42
     */
    DD_MM_YYYY_HH_MM_SS("dd.MM.yyyy HH:mm:ss", DateTimeAwareness.DATE_AND_TIME),

    /**
     * dd.MM.yyyy HH:mm:ss, like 23.02.2007 13:37:42
     */
    DD_MM_YYYY_HH_MM_SS_SSS("dd.MM.yyyy HH:mm:ss.SSS", DateTimeAwareness.DATE_AND_TIME),

    /**
     * yyyyMMdd, like 20070223
     */
    YYYYMMDD("yyyyMMdd", DateTimeAwareness.DATE_ONLY),

    /**
     * yyyyMMddHHmm, like 200702231337
     */
    YYYYMMDDHHMM("yyyyMMddHHmm", DateTimeAwareness.DATE_AND_TIME),

    /**
     * yyyyMMddHHmmss, like 20070223133742
     */
    YYYYMMDDHHMMSS("yyyyMMddHHmmss", DateTimeAwareness.DATE_AND_TIME),

    /**
     * yyyy.MM.dd, like 2007.02.23
     */
    YYYY_MM_DD("yyyy.MM.dd", DateTimeAwareness.DATE_ONLY),

    /**
     * yyyy.MM.dd HH:mm, like 2007.02.23 13:37
     */
    YYYY_MM_DD_HH_MM("yyyy.MM.dd HH:mm", DateTimeAwareness.DATE_AND_TIME),

    /**
     * yyyy.MM.dd HH:mm:ss, like 2007.02.23 13:37:42
     */
    YYYY_MM_DD_HH_MM_SS("yyyy.MM.dd HH:mm:ss", DateTimeAwareness.DATE_AND_TIME),

    /**
     * yyyy.MM.dd HH:mm:ss, like 2007.02.23 13:37:42
     */
    YYYY_MM_DD_HH_MM_SS_SSS("yyyy.MM.dd HH:mm:ss.SSS", DateTimeAwareness.DATE_AND_TIME),

    /**
     * yyyy-MM-dd, like 2007-02-23
     */
    ISO8601DateOnly("yyyy-MM-dd", DateTimeAwareness.DATE_ONLY),

    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSZZ, like 2007-02-23T21:11:13.370+01:00
     */
    ISO8601DateTimeWithMillis("yyyy-MM-dd'T'HH:mm:ss.SSSZZ", DateTimeAwareness.DATE_AND_TIME),

    /**
     * yyyy-MM-dd'T'HH:mm:ssZZ, like 2007-02-23T21:11:13+01:00
     */
    ISO8601DateTime("yyyy-MM-dd'T'HH:mm:ssZZ", DateTimeAwareness.DATE_AND_TIME);

    private static enum DateTimeAwareness {
        DATE_ONLY, DATE_AND_TIME, TIME_ONLY;
    }

    private static final DateTime REFERENCE_DATETIME = new DateTime(2007, 2, 23, 21, 11, 13, 37);

    /**
     * Examples are used to calculate the actual expected length of a transformed date string.
     * In addition they conveniently demonstrates how a "real life" date will look like for
     * a given TemporalFormat.
     */
    private static final Map<TemporalFormat, String> examples = ImmutableMap.<TemporalFormat, String>builder()
            .put(DD_MM_YYYY, DD_MM_YYYY.formatter.print(REFERENCE_DATETIME))
            .put(DD_MM_YY, DD_MM_YY.formatter.print(REFERENCE_DATETIME))
            .put(DD_MM_YYYY_HH_MM, DD_MM_YYYY_HH_MM.formatter.print(REFERENCE_DATETIME))
            .put(DD_MM_YYYY_HH_MM_SS, DD_MM_YYYY_HH_MM_SS.formatter.print(REFERENCE_DATETIME))
            .put(DD_MM_YYYY_HH_MM_SS_SSS, DD_MM_YYYY_HH_MM_SS_SSS.formatter.print(REFERENCE_DATETIME))
            .put(YYYYMMDD, YYYYMMDD.formatter.print(REFERENCE_DATETIME))
            .put(YYYYMMDDHHMM, YYYYMMDDHHMM.formatter.print(REFERENCE_DATETIME))
            .put(YYYYMMDDHHMMSS, YYYYMMDDHHMMSS.formatter.print(REFERENCE_DATETIME))
            .put(YYYY_MM_DD, YYYY_MM_DD.formatter.print(REFERENCE_DATETIME))
            .put(YYYY_MM_DD_HH_MM, YYYY_MM_DD_HH_MM.formatter.print(REFERENCE_DATETIME))
            .put(YYYY_MM_DD_HH_MM_SS, YYYY_MM_DD_HH_MM_SS.formatter.print(REFERENCE_DATETIME))
            .put(YYYY_MM_DD_HH_MM_SS_SSS, YYYY_MM_DD_HH_MM_SS_SSS.formatter.print(REFERENCE_DATETIME))
            .put(ISO8601DateOnly, ISO8601DateOnly.formatter.print(REFERENCE_DATETIME))
            .put(ISO8601DateTimeWithMillis, ISO8601DateTimeWithMillis.formatter.print(REFERENCE_DATETIME))
            .put(ISO8601DateTime, ISO8601DateTime.formatter.print(REFERENCE_DATETIME))
            .build();

    private final DateTimeFormatter formatter;
    private final DateTimeAwareness dateTimeAwareness;
    private final String pattern;

    private TemporalFormat(String pattern, DateTimeAwareness dateTimeAwareness) {
        this.formatter = DateTimeFormat.forPattern(pattern);
        this.dateTimeAwareness = dateTimeAwareness;
        this.pattern = pattern;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public boolean isDateAware() {
        return dateTimeAwareness == DateTimeAwareness.DATE_AND_TIME || dateTimeAwareness == DateTimeAwareness.DATE_ONLY;
    }

    public boolean isTimeAware() {
        return dateTimeAwareness == DateTimeAwareness.DATE_AND_TIME || dateTimeAwareness == DateTimeAwareness.TIME_ONLY;
    }

    public boolean isDateAndTimeAware() {
        return dateTimeAwareness == DateTimeAwareness.DATE_AND_TIME;
    }

    public String getPattern() {
        return pattern;
    }

    public String exampleString() {
        return examples.get(this);
    }

    public int length() {
        return exampleString().length();
    }

    @VisibleForTesting
    static Map<TemporalFormat, String> examples() {
        return examples;
    }
}
