package com.github.kschulst.smorph.converters.nullsafe;

import com.google.common.base.Function;
import com.github.kschulst.smorph.base.TemporalFormat;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;
import com.github.kschulst.smorph.converters.formatters.ToDateString;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Objects.firstNonNull;
import static com.github.kschulst.smorph.base.TemporalFormat.ISO8601DateOnly;
import static com.github.kschulst.smorph.base.TemporalFormat.ISO8601DateTimeWithMillis;
import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToInterval {
    private ToInterval() {}

    private static final String SEPARATOR = "/";

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromString(@Nonnull final TemporalFormat format, @Nonnull final String separator) {
        return new Function<Conversion<CharSequence, Interval>, Interval>() {
            public Interval apply(Conversion<CharSequence, Interval> conversion) {
                if (conversion.fromValue().length() != (format.length() + format.length() + separator.length())) {
                    return conversion.resolveDefault("Invalid interval string. Separator: '" + separator + "', format: '" + format.getPattern() + "'");
                }

                CharSequence s1 = conversion.fromValue().subSequence(0, format.length());
                CharSequence s2 = conversion.fromValue().subSequence(format.length() + separator.length(), conversion.fromValue().length());
                DateTime d1 = ToDateTime.from(s1, format);
                DateTime d2 = ToDateTime.from(s2, format);

                if (d1 == null || d2 == null) {
                    return conversion.resolveDefault("Invalid format");
                }

                return new Interval(d1, d2);
            }
        };
    }

    // ------------------------------------------------------------------------
    // Converters
    // ------------------------------------------------------------------------

    public static Converter<CharSequence, Interval> convert(@Nullable CharSequence intervalString, @Nonnull TemporalFormat format, @Nonnull String separator) {
        return transform(intervalString, ToInterval.fromString(format, separator)).trimInput();
    }

    public static Interval from(@Nullable CharSequence intervalString, @Nonnull TemporalFormat format, @Nonnull String separator) {
        return convert(intervalString, format, separator).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, Interval> convertForYear(@Nullable String year) {
        return convert(year + "-01-01" + SEPARATOR + year + "-12-31", ISO8601DateOnly, SEPARATOR);
    }

    public static Interval forYear(@Nullable String year) {
        return convertForYear(year).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, Interval> convert(@Nullable CharSequence start, @Nullable CharSequence end, @Nonnull TemporalFormat format) {
        return convert(firstNonNull(start, "").toString().trim() + SEPARATOR + firstNonNull(end, "").toString().trim(), format, SEPARATOR);
    }

    public static Interval from(@Nullable CharSequence start, @Nullable CharSequence end, @Nonnull TemporalFormat format) {
        return convert(start, end, format).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, Interval> convert(@Nullable DateTime start, @Nullable DateTime end) {
        return convert(start + SEPARATOR + end, ISO8601DateTimeWithMillis, SEPARATOR);
    }

    public static Interval from(@Nullable DateTime start, @Nullable DateTime end) {
        return convert(start, end).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, Interval> convert(@Nullable LocalDate start, @Nullable LocalDate end) {
        return convert(start + SEPARATOR + end, ISO8601DateOnly, SEPARATOR);
    }

    public static Interval from(@Nullable LocalDate start, @Nullable LocalDate end) {
        return convert(start, end).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, Interval> convert(@Nullable Date start, @Nullable Date end) {
        return convert(ToDateString.from(start, ISO8601DateTimeWithMillis) + SEPARATOR + ToDateString.from(end, ISO8601DateTimeWithMillis), ISO8601DateTimeWithMillis, SEPARATOR);
    }

    public static Interval from(@Nullable Date start, @Nullable Date end) {
        return convert(start, end).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, Interval> convert(@Nullable Calendar start, @Nullable Calendar end) {
        return convert(ToDateString.from(start, ISO8601DateTimeWithMillis) + SEPARATOR + ToDateString.from(end, ISO8601DateTimeWithMillis), ISO8601DateTimeWithMillis, SEPARATOR);
    }

    public static Interval from(@Nullable Calendar start, @Nullable Calendar end) {
        return convert(start, end).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, Interval> convert(@Nullable XMLGregorianCalendar start, @Nullable XMLGregorianCalendar end) {
        return convert(ToDateString.from(start, ISO8601DateTimeWithMillis) + SEPARATOR + ToDateString.from(end, ISO8601DateTimeWithMillis), ISO8601DateTimeWithMillis, SEPARATOR);
    }

    public static Interval from(@Nullable XMLGregorianCalendar start, @Nullable XMLGregorianCalendar end) {
        return convert(start, end).withNullAsDefaultValue();
    }

}
