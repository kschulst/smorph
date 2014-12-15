package com.github.kschulst.smorph.converters.nullsafe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.github.kschulst.smorph.base.TemporalFormat;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToXMLGregorianCalendar {
    private ToXMLGregorianCalendar() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromStringAsDateOnly(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<CharSequence, XMLGregorianCalendar>, XMLGregorianCalendar>() {
            public XMLGregorianCalendar apply(Conversion<CharSequence, XMLGregorianCalendar> conversion) {
                DateTime dt = ToDateTime.from(conversion.fromValue(), format);
                return withDateOnly(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
            }
        };
    }

    public static Function fromStringAsDateTime(@Nonnull final TemporalFormat format) {
        return new Function<Conversion<CharSequence, XMLGregorianCalendar>, XMLGregorianCalendar>() {
            public XMLGregorianCalendar apply(Conversion<CharSequence, XMLGregorianCalendar> conversion) {
                return withDateAndTime(ToDateTime.from(conversion.fromValue(), format));
            }
        };
    }


    public static final Function fromCalendarAsDateOnly = new Function<Conversion<Calendar, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<Calendar, XMLGregorianCalendar> conversion) {
            LocalDate d = new LocalDate(conversion.fromValue());
            return withDateOnly(d.getYear(), d.getMonthOfYear(), d.getDayOfMonth());
        }
    };

    public static final Function fromDateAsDateOnly = new Function<Conversion<Date, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<Date, XMLGregorianCalendar> conversion) {
            LocalDate d = new LocalDate(conversion.fromValue());
            return withDateOnly(d.getYear(), d.getMonthOfYear(), d.getDayOfMonth());
        }
    };

    public static final Function fromLocalDateAsDateOnly = new Function<Conversion<LocalDate, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<LocalDate, XMLGregorianCalendar> conversion) {
            return withDateOnly(conversion.fromValue().getYear(), conversion.fromValue().getMonthOfYear(), conversion.fromValue().getDayOfMonth());
        }
    };

    public static final Function fromDateTimeAsDateOnly = new Function<Conversion<DateTime, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<DateTime, XMLGregorianCalendar> conversion) {
            return withDateOnly(conversion.fromValue().getYear(), conversion.fromValue().getMonthOfYear(), conversion.fromValue().getDayOfMonth());
        }
    };

    public static final Function fromCalendarAsDateTime = new Function<Conversion<Calendar, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<Calendar, XMLGregorianCalendar> conversion) {
            DateTime d = new DateTime(conversion.fromValue());
            return withDateAndTime(d);
        }
    };

    public static final Function fromDateAsDateTime = new Function<Conversion<Date, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<Date, XMLGregorianCalendar> conversion) {
            return withDateAndTime(new DateTime(conversion.fromValue()));
        }
    };

    public static final Function fromLocalDateAsDateTime = new Function<Conversion<LocalDate, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<LocalDate, XMLGregorianCalendar> conversion) {
            return withDateAndTime(conversion.fromValue().toDateTimeAtStartOfDay());
        }
    };

    public static final Function fromDateTimeAsDateTime = new Function<Conversion<DateTime, XMLGregorianCalendar>, XMLGregorianCalendar>() {
        public XMLGregorianCalendar apply(Conversion<DateTime, XMLGregorianCalendar> conversion) {
            return withDateAndTime(conversion.fromValue());
        }
    };

    @VisibleForTesting
    static XMLGregorianCalendar withDateOnly(int year, int month, int day) {
        try {
            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            xmlGregorianCalendar.setYear(year);
            xmlGregorianCalendar.setMonth(month);
            xmlGregorianCalendar.setDay(day);
            return xmlGregorianCalendar;
        }
        catch (Exception e) {
            return null;
        }
    }

    @VisibleForTesting
    static XMLGregorianCalendar withDateAndTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond, 1);
        }
        catch (Exception e) {
            return null;
        }
    }

    @VisibleForTesting
    static XMLGregorianCalendar withDateAndTime(@Nullable DateTime dateTime) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), dateTime.getSecondOfMinute(), dateTime.getMillisOfSecond(), 1);
        }
        catch (Exception e) {
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // Converters
    // ------------------------------------------------------------------------

    public static Converter<Calendar, XMLGregorianCalendar> convertAsDateOnly(@Nullable Calendar calendar) {
        return transform(calendar, ToXMLGregorianCalendar.fromCalendarAsDateOnly);
    }

    public static XMLGregorianCalendar asDateOnly(@Nullable Calendar calendar) {
        return convertAsDateOnly(calendar).withNullAsDefaultValue();
    }

    public static Converter<LocalDate, XMLGregorianCalendar> convertAsDateOnly(@Nullable LocalDate localDate) {
        return transform(localDate, ToXMLGregorianCalendar.fromLocalDateAsDateOnly);
    }

    public static XMLGregorianCalendar asDateOnly(@Nullable LocalDate localDate) {
        return convertAsDateOnly(localDate).withNullAsDefaultValue();
    }

    public static Converter<DateTime, XMLGregorianCalendar> convertAsDateOnly(@Nullable DateTime dateTime) {
        return transform(dateTime, ToXMLGregorianCalendar.fromDateTimeAsDateOnly);
    }

    public static XMLGregorianCalendar asDateOnly(@Nullable DateTime dateTime) {
        return convertAsDateOnly(dateTime).withNullAsDefaultValue();
    }

    public static Converter<Date, XMLGregorianCalendar> convertAsDateOnly(@Nullable Date date) {
        return transform(date, ToXMLGregorianCalendar.fromDateAsDateOnly);
    }

    public static XMLGregorianCalendar asDateOnly(@Nullable Date date) {
        return convertAsDateOnly(date).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, XMLGregorianCalendar> convertAsDateOnly(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return transform(dateString, ToXMLGregorianCalendar.fromStringAsDateOnly(format));
    }

    public static XMLGregorianCalendar asDateOnly(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return convertAsDateOnly(dateString, format).withNullAsDefaultValue();
    }

    public static Converter<Calendar, XMLGregorianCalendar> convertCalendarAsDateOnly(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convertAsDateOnly(ToValue.from(jaxbElement));
    }

    public static XMLGregorianCalendar fromCalendarAsDateOnly(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convertCalendarAsDateOnly(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Date, XMLGregorianCalendar> convertDateAsDateOnly(@Nullable JAXBElement<Date> jaxbElement) {
        return convertAsDateOnly(ToValue.from(jaxbElement));
    }

    public static XMLGregorianCalendar fromDateAsDateOnly(@Nullable JAXBElement<Date> jaxbElement) {
        return convertDateAsDateOnly(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Calendar, XMLGregorianCalendar> convertAsDateTime(@Nullable Calendar calendar) {
        return transform(calendar, ToXMLGregorianCalendar.fromCalendarAsDateTime);
    }

    public static XMLGregorianCalendar asDateTime(@Nullable Calendar calendar) {
        return convertAsDateTime(calendar).withNullAsDefaultValue();
    }

    public static Converter<LocalDate, XMLGregorianCalendar> convertAsDateTime(@Nullable LocalDate localDate) {
        return transform(localDate, ToXMLGregorianCalendar.fromLocalDateAsDateTime);
    }

    public static XMLGregorianCalendar asDateTime(@Nullable LocalDate localDate) {
        return convertAsDateTime(localDate).withNullAsDefaultValue();
    }

    public static Converter<DateTime, XMLGregorianCalendar> convertAsDateTime(@Nullable DateTime dateTime) {
        return transform(dateTime, ToXMLGregorianCalendar.fromDateTimeAsDateTime);
    }

    public static XMLGregorianCalendar asDateTime(@Nullable DateTime dateTime) {
        return convertAsDateTime(dateTime).withNullAsDefaultValue();
    }

    public static Converter<Date, XMLGregorianCalendar> convertAsDateTime(@Nullable Date date) {
        return transform(date, ToXMLGregorianCalendar.fromDateAsDateTime);
    }

    public static XMLGregorianCalendar asDateTime(@Nullable Date date) {
        return convertAsDateTime(date).withNullAsDefaultValue();
    }

    public static Converter<CharSequence, XMLGregorianCalendar> convertAsDateTime(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return transform(dateString, ToXMLGregorianCalendar.fromStringAsDateTime(format));
    }

    public static XMLGregorianCalendar asDateTime(@Nullable CharSequence dateString, @Nonnull TemporalFormat format) {
        return convertAsDateTime(dateString, format).withNullAsDefaultValue();
    }

    public static Converter<Calendar, XMLGregorianCalendar> convertCalendarAsDateTime(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convertAsDateTime(ToValue.from(jaxbElement));
    }

    public static XMLGregorianCalendar fromCalendarAsDateTime(@Nullable JAXBElement<Calendar> jaxbElement) {
        return convertCalendarAsDateTime(jaxbElement).withNullAsDefaultValue();
    }

    public static Converter<Date, XMLGregorianCalendar> convertDateAsDateTime(@Nullable JAXBElement<Date> jaxbElement) {
        return convertAsDateTime(ToValue.from(jaxbElement));
    }

    public static XMLGregorianCalendar fromDateAsDateTime(@Nullable JAXBElement<Date> jaxbElement) {
        return convertDateAsDateTime(jaxbElement).withNullAsDefaultValue();
    }

    public static XMLGregorianCalendar from(@Nullable JAXBElement<XMLGregorianCalendar> jaxbElement) {
        return ToValue.from(jaxbElement);
    }

}
