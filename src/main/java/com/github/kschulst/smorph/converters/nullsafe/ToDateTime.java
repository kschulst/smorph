package com.github.kschulst.smorph.converters.nullsafe;

import com.google.common.base.Function;
import com.github.kschulst.smorph.base.TemporalFormat;
import com.github.kschulst.smorph.base.XMLGregorianCalendars;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToDateTime {
    private ToDateTime() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromString(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<CharSequence, DateTime>, DateTime>() {
            public DateTime apply(Conversion<CharSequence, DateTime> conversion) {
                try {
                    return format.getFormatter().parseDateTime(conversion.fromValue().toString());
                }
                catch (Exception e) {
                    return conversion.resolveDefault("Dateformat: " + format.getPattern(), e);
                }
            }
        };
    }

    public static final Function fromXMLGregorianCal = new Function<Conversion<XMLGregorianCalendar, DateTime>, DateTime>() {
        public DateTime apply(Conversion<XMLGregorianCalendar, DateTime> conversion) {
            if (XMLGregorianCalendars.isDateTime(conversion.fromValue())) {

                int millisecond = conversion.fromValue().getMillisecond();

                return new DateTime(
                        conversion.fromValue().getYear(),
                        conversion.fromValue().getMonth(),
                        conversion.fromValue().getDay(),
                        conversion.fromValue().getHour(),
                        conversion.fromValue().getMinute(),
                        conversion.fromValue().getSecond(),
                        millisecond != DatatypeConstants.FIELD_UNDEFINED ? millisecond : 0
                );
            }
            else {
                return new DateTime(
                        conversion.fromValue().getYear(),
                        conversion.fromValue().getMonth(),
                        conversion.fromValue().getDay(),
                        0, 0, 0, 0
                );
            }

        }
    };

    public static final Function fromCalendar = new Function<Conversion<Calendar, DateTime>, DateTime>() {
        public DateTime apply(Conversion<Calendar, DateTime> conversion) {
            return new DateTime(conversion.fromValue().getTime());
        }
    };

    public static final Function fromDate = new Function<Conversion<Date, DateTime>, DateTime>() {
        public DateTime apply(Conversion<Date, DateTime> conversion) {
            return new DateTime(conversion.fromValue());
        }
    };

    public static final Function fromLocalDate = new Function<Conversion<LocalDate, DateTime>, DateTime>() {
        public DateTime apply(Conversion<LocalDate, DateTime> conversion) {
            return conversion.fromValue().toDateTimeAtStartOfDay();
        }
    };

    // ------------------------------------------------------------------------
    // Converters
    // ------------------------------------------------------------------------

    public static Converter<Calendar, DateTime> convert(@Nullable Calendar calendar) {
        return transform(calendar, ToDateTime.fromCalendar);
    }

    public static DateTime from(@Nullable Calendar calendar) {
        return convert(calendar).withNullAsDefaultValue();
    }

    public static Converter<XMLGregorianCalendar, DateTime> convert(@Nullable XMLGregorianCalendar calendar) {
        return transform(calendar, ToDateTime.fromXMLGregorianCal);
    }

    public static DateTime from(@Nullable XMLGregorianCalendar calendar) {
        return convert(calendar).withNullAsDefaultValue();
    }

    public static Converter<LocalDate, DateTime> convert(@Nullable LocalDate localDate) {
        return transform(localDate, ToDateTime.fromLocalDate);
    }

    public static DateTime from(@Nullable LocalDate localDate) {
        return convert(localDate).withNullAsDefaultValue();
    }

    public static Converter<Date, DateTime> convert(@Nullable Date date) {
        return transform(date, ToDateTime.fromDate);
    }

    public static DateTime from(@Nullable Date date) {
        return convert(date).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, DateTime> convert(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return transform(dateString, ToDateTime.fromString(format));
    }

    public static DateTime from(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return convert(dateString, format).trimInput().withNullAsDefaultValue();
    }

    public static Converter<XMLGregorianCalendar, DateTime> convertXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static DateTime fromXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convertXMLGregorianCalendar(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Calendar, DateTime> convertCalendar(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static DateTime fromCalendar(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convertCalendar(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Date, DateTime> convertDate(@Nullable JAXBElement<Date> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static DateTime fromDate(@Nullable JAXBElement<Date> jaxbElement) {
        return convertDate(jaxbElement).withNullAsDefaultValue();
    }

    public static DateTime forStartOfYear(@Nonnull CharSequence yyyy) {
        return convert(yyyy + "-01-01", TemporalFormat.ISO8601DateOnly).withNullAsDefaultValue();
    }

    public static DateTime forEndOfYear(@Nonnull String yyyy) {
        return convert(yyyy + "-12-31", TemporalFormat.ISO8601DateOnly).withNullAsDefaultValue();
    }
}
