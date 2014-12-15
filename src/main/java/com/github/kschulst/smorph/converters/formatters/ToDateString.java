package com.github.kschulst.smorph.converters.formatters;

import com.google.common.base.Function;
import com.github.kschulst.smorph.base.TemporalFormat;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;
import com.github.kschulst.smorph.converters.nullsafe.ToDateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToDateString {
    private ToDateString() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromReadablePartial(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<ReadablePartial, String>, String>() {
            public String apply(Conversion<ReadablePartial, String> conversion) {
                return unknownCharsToZero(format.getFormatter().print(conversion.fromValue()));
            }
        };
    }

    public static Function fromReadableInstant(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<ReadableInstant, String>, String>() {
            public String apply(Conversion<ReadableInstant, String> conversion) {
                return unknownCharsToZero(format.getFormatter().print(conversion.fromValue()));
            }
        };
    }

    private static String unknownCharsToZero(String s) {
        return (s == null) ? null : s.replaceAll("�", "0");
    }

    // ------------------------------------------------------------------------
    // Formatters
    // ------------------------------------------------------------------------

    public static Converter<ReadablePartial, String> format(@Nullable ReadablePartial date, @Nonnull TemporalFormat format) {
        return transform(date, ToDateString.fromReadablePartial(format));
    }

    public static String from(@Nullable ReadablePartial date, @Nonnull TemporalFormat format) {
        return format(date, format).withNullAsDefaultValue();
    }

    public static Converter<ReadableInstant, String> format(@Nullable ReadableInstant date, @Nonnull TemporalFormat format) {
        return transform(date, ToDateString.fromReadableInstant(format));
    }

    public static String from(@Nullable ReadableInstant date, @Nonnull TemporalFormat format) {
        return format(date, format).withNullAsDefaultValue();
    }

    public static Converter<Date, String> format(@Nullable Date date, @Nonnull TemporalFormat format) {
        return transform(ToDateTime.from(date), ToDateString.fromReadableInstant(format));
    }

    public static String from(@Nullable Date date, @Nonnull TemporalFormat format) {
        return format(date, format).withNullAsDefaultValue();
    }

    public static Converter<Calendar, String> format(@Nullable Calendar calendar, @Nonnull TemporalFormat format) {
        return transform(ToDateTime.from(calendar), ToDateString.fromReadableInstant(format));
    }

    public static String from(@Nullable Calendar calendar, @Nonnull TemporalFormat format) {
        return format(calendar, format).withNullAsDefaultValue();
    }

    public static Converter<XMLGregorianCalendar, String> format(@Nullable XMLGregorianCalendar calendar, @Nonnull TemporalFormat format) {
        return transform(ToDateTime.from(calendar), ToDateString.fromReadableInstant(format));
    }

    public static String from(@Nullable XMLGregorianCalendar calendar, @Nonnull TemporalFormat format) {
        return format(calendar, format).withNullAsDefaultValue();
    }

    // TODO Tests for XMLGregorianCalendar
    // TODO Add Interval
}
