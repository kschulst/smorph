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
import java.util.Locale;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToCalendar {
    private ToCalendar() {}

    private static final Locale LOCALE = new Locale("nb", "NO");

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromString(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<CharSequence, Calendar>, Calendar>() {
            public Calendar apply(Conversion<CharSequence, Calendar> conversion) {
                try {
                    return format.getFormatter().parseDateTime(conversion.fromValue().toString()).toCalendar(LOCALE);
                }
                catch (Exception e) {
                    return conversion.resolveDefault("Dateformat: " + format.getPattern(), e);
                }
            }
        };
    }

    public static final Function fromLocalDate = new Function<Conversion<LocalDate, Calendar>, Calendar>() {
        public Calendar apply(Conversion<LocalDate, Calendar> conversion) {
            return conversion.fromValue().toDateTimeAtStartOfDay().toCalendar(LOCALE);
        }
    };

    public static final Function fromDateTime = new Function<Conversion<DateTime, Calendar>, Calendar>() {
        public Calendar apply(Conversion<DateTime, Calendar> conversion) {
            return conversion.fromValue().toCalendar(LOCALE);
        }
    };

    public static final Function fromDate = new Function<Conversion<Date, Calendar>, Calendar>() {
        public Calendar apply(Conversion<Date, Calendar> conversion) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(conversion.fromValue());
            return cal;
        }
    };

    public static final Function fromXMLGregorianCalendar = new Function<Conversion<XMLGregorianCalendar, Calendar>, Calendar>() {
        public Calendar apply(Conversion<XMLGregorianCalendar, Calendar> conversion) {
            return ToDateTime.from(conversion.fromValue()).toCalendar(LOCALE);
        }
    };

    // ------------------------------------------------------------------------
    // Converters
    // ------------------------------------------------------------------------

    public static Converter<CharSequence, Calendar> convert(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return transform(dateString, ToCalendar.fromString(format));
    }

    public static Calendar from(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return convert(dateString, format).trimInput().withNullAsDefaultValue();
    }

    public static Converter<LocalDate, Calendar> convert(@Nullable LocalDate localDate) {
        return transform(localDate, ToCalendar.fromLocalDate);
    }

    public static Calendar from(@Nullable LocalDate localDate) {
        return convert(localDate).withNullAsDefaultValue();
    }

    public static Converter<DateTime, Calendar> convert(@Nullable DateTime dateTime) {
        return transform(dateTime, ToCalendar.fromDateTime);
    }

    public static Calendar from(@Nullable DateTime dateTime) {
        return convert(dateTime).withNullAsDefaultValue();
    }

    public static Converter<Date, Calendar> convert(@Nullable Date date) {
        return transform(date, ToCalendar.fromDate);
    }

    public static Calendar from(@Nullable Date date) {
        return convert(date).withNullAsDefaultValue();
    }

    public static Converter<XMLGregorianCalendar, Calendar> convert(@Nullable XMLGregorianCalendar xmlGregorianCalendar) {
        return transform(xmlGregorianCalendar, ToCalendar.fromXMLGregorianCalendar);
    }

    public static Calendar from(@Nullable XMLGregorianCalendar xmlGregorianCalendar) {
        return convert(xmlGregorianCalendar).withNullAsDefaultValue();
    }

    public static Calendar from(@Nullable JAXBElement<Calendar> jaxbElement) {
        return ToValue.from(jaxbElement);
    }

    public static Converter<XMLGregorianCalendar, Calendar> convertXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static Calendar fromXMLGregorianCalendar(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return convertXMLGregorianCalendar(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Date, Calendar> convertDate(@Nullable JAXBElement<Date> jaxbElement) {
        return convert(ToValue.from(jaxbElement));
    }

    public static Calendar fromDate(@Nullable JAXBElement<Date> jaxbElement) {
        return convertDate(jaxbElement).withNullAsDefaultValue();
    }

}
