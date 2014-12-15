package com.github.kschulst.smorph.converters.nullsafe;

import com.google.common.base.Function;
import com.github.kschulst.smorph.base.TemporalFormat;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToLocalDate {
    private ToLocalDate() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromString(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<CharSequence, LocalDate>, LocalDate>() {
            public LocalDate apply(Conversion<CharSequence, LocalDate> conversion) {
                try {
                    return format.getFormatter().parseLocalDate(conversion.fromValue().toString());
                }
                catch (Exception e) {
                    return conversion.resolveDefault("Dateformat: " + format.getPattern(), e);
                }
            }
        };
    }

    public static final Function fromXMLGregorianCalendar = new Function<Conversion<XMLGregorianCalendar, LocalDate>, LocalDate>() {
        public LocalDate apply(Conversion<XMLGregorianCalendar, LocalDate> conversion) {
            return new LocalDate(conversion.fromValue().getYear(), conversion.fromValue().getMonth(), conversion.fromValue().getDay());
        }
    };

    public static final Function fromCalendar = new Function<Conversion<Calendar, LocalDate>, LocalDate>() {
        public LocalDate apply(Conversion<Calendar, LocalDate> conversion) {
            return new LocalDate(conversion.fromValue().getTime());
        }
    };

    public static final Function fromDate = new Function<Conversion<Date, LocalDate>, LocalDate>() {
        public LocalDate apply(Conversion<Date, LocalDate> conversion) {
            return LocalDate.fromDateFields(conversion.fromValue());
        }
    };

    public static final Function fromDateTime = new Function<Conversion<DateTime, LocalDate>, LocalDate>() {
        public LocalDate apply(Conversion<DateTime, LocalDate> conversion) {
            return conversion.fromValue().toLocalDate();
        }
    };

    // ------------------------------------------------------------------------
    // Converters
    // ------------------------------------------------------------------------

    public static Converter<Calendar, LocalDate> convert(@Nullable Calendar calendar) {
        return transform(calendar, ToLocalDate.fromCalendar);
    }

    public static LocalDate from(@Nullable Calendar calendar) {
        return convert(calendar).withNullAsDefaultValue();
    }

    public static Converter<XMLGregorianCalendar, LocalDate> convert(@Nullable XMLGregorianCalendar calendar) {
        return transform(calendar, ToLocalDate.fromXMLGregorianCalendar);
    }

    public static LocalDate from(@Nullable XMLGregorianCalendar calendar) {
        return convert(calendar).withNullAsDefaultValue();
    }

    public static Converter<DateTime, LocalDate> convert(@Nullable DateTime dateTime) {
        return transform(dateTime, ToLocalDate.fromDateTime);
    }

    public static LocalDate from(@Nullable DateTime dateTime) {
        return convert(dateTime).withNullAsDefaultValue();
    }

    public static Converter<Date, LocalDate> convert(@Nullable Date date) {
        return transform(date, ToLocalDate.fromDate);
    }

    public static LocalDate from(@Nullable Date date) {
        return convert(date).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, LocalDate> convert(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return transform(dateString, ToLocalDate.fromString(format));
    }

    public static LocalDate from(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return convert(dateString, format).trimInput().withNullAsDefaultValue();
    }

    public static Converter<XMLGregorianCalendar, LocalDate> convertXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static LocalDate fromXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convertXMLGregorianCalendar(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Calendar, LocalDate> convertCalendar(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static LocalDate fromCalendar(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convertCalendar(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Date, LocalDate> convertDate(@Nullable JAXBElement<Date> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static LocalDate fromDate(@Nullable JAXBElement<Date> jaxbElement) {
        return convertDate(jaxbElement).withNullAsDefaultValue();
    }

    public static LocalDate forStartOfYear(@Nonnull CharSequence yyyy) {
        return convert(yyyy + "-01-01", TemporalFormat.ISO8601DateOnly).withNullAsDefaultValue();
    }

    public static LocalDate forEndOfYear(@Nonnull String yyyy) {
        return convert(yyyy + "-12-31", TemporalFormat.ISO8601DateOnly).withNullAsDefaultValue();
    }

}
