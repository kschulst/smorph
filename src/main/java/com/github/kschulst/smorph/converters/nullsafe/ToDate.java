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

public final class ToDate {
    private ToDate() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromString(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<CharSequence, Date>, Date>() {
            public Date apply(Conversion<CharSequence, Date> conversion) {
                try {
                    return format.getFormatter().parseDateTime(conversion.fromValue().toString()).toDate();
                }
                catch (Exception e) {
                    return conversion.resolveDefault("Dateformat: " + format.getPattern(), e);
                }
            }
        };
    }

    public static final Function fromXMLGregorianCalendar = new Function<Conversion<XMLGregorianCalendar, Date>, Date>() {
        public Date apply(Conversion<XMLGregorianCalendar, Date> conversion) {
            return ToDateTime.from(conversion.fromValue()).toDate();
        }
    };

    public static final Function fromCalendar = new Function<Conversion<Calendar, Date>, Date>() {
        public Date apply(Conversion<Calendar, Date> conversion) {
            return conversion.fromValue().getTime();
        }
    };
    public static final Function fromDateTime = new Function<Conversion<DateTime, Date>, Date>() {
        public Date apply(Conversion<DateTime, Date> conversion) {
            return conversion.fromValue().toDate();
        }
    };

    public static final Function fromLocalDate = new Function<Conversion<LocalDate, Date>, Date>() {
        public Date apply(Conversion<LocalDate, Date> conversion) {
            return conversion.fromValue().toDate();
        }
    };

    // ------------------------------------------------------------------------
    // Converters
    // ------------------------------------------------------------------------

    public static Converter<CharSequence, Date> convert(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return transform(dateString, ToDate.fromString(format));
    }

    public static Date from(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return convert(dateString, format).trimInput().withNullAsDefaultValue();
    }

    public static Converter<Calendar, Date> convert(@Nullable Calendar calendar) {
        return transform(calendar, ToDate.fromCalendar);
    }

    public static Date from(@Nullable Calendar calendar) {
        return convert(calendar).withNullAsDefaultValue();
    }

    public static Converter<XMLGregorianCalendar, Date> convert(@Nullable XMLGregorianCalendar calendar) {
        return transform(calendar, ToDate.fromXMLGregorianCalendar);
    }

    public static Date from(@Nullable XMLGregorianCalendar calendar) {
        return convert(calendar).withNullAsDefaultValue();
    }

    public static Converter<LocalDate, Date> convert(@Nullable LocalDate localDate) {
        return transform(localDate, ToDate.fromLocalDate);
    }

    public static Date from(@Nullable LocalDate localDate) {
        return convert(localDate).withNullAsDefaultValue();
    }

    public static Converter<DateTime, Date> convert(@Nullable DateTime dateTime) {
        return transform(dateTime, ToDate.fromDateTime);
    }

    public static Date from(@Nullable DateTime dateTime) {
        return convert(dateTime).withNullAsDefaultValue();
    }

    public static Date from(@Nullable JAXBElement<Date> jaxbElement) {
        return ToValue.from(jaxbElement);
    }

    public static Converter<XMLGregorianCalendar, Date> convertXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static Date fromXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convertXMLGregorianCalendar(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Calendar, Date> convertCalendar(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static Date fromCalendar(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convertCalendar(jaxbElement).withNullAsDefaultValue();
    }

    public static Date forStartOfYear(@Nonnull CharSequence yyyy) {
        return convert(yyyy + "-01-01", TemporalFormat.ISO8601DateOnly).withNullAsDefaultValue();
    }

    public static Date forEndOfYear(@Nonnull String yyyy) {
        return convert(yyyy + "-12-31", TemporalFormat.ISO8601DateOnly).withNullAsDefaultValue();
    }

}
