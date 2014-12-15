package com.github.kschulst.smorph.converters.nullsafe;

import com.google.common.base.Function;
import com.google.common.primitives.UnsignedInteger;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToNumber {
    private ToNumber() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static final Function fromNumberAsInteger = new Function<Conversion<Number, Integer>, Integer>() {
        public Integer apply(Conversion<Number, Integer> conversion) {
            // TODO: Log warning if we're scaling down anything that results in loss of precision
            return conversion.fromValue().intValue();
        }
    };

    public static final Function fromStringAsInteger = new Function<Conversion<String, Integer>, Integer>() {
        public Integer apply(Conversion<String, Integer> conversion) {
            return Integer.parseInt(conversion.fromValue());
        }
    };

    public static final Function fromNumberAsLong = new Function<Conversion<Number, Long>, Long>() {
        public Long apply(Conversion<Number, Long> conversion) {
            return conversion.fromValue().longValue();
        }
    };

    public static final Function fromStringAsLong = new Function<Conversion<String, Long>, Long>() {
        public Long apply(Conversion<String, Long> conversion) {
            return Long.parseLong(conversion.fromValue());
        }
    };

    public static final Function fromNumberAsDouble = new Function<Conversion<Number, Double>, Double>() {
        public Double apply(Conversion<Number, Double> conversion) {
            return conversion.fromValue().doubleValue();
        }
    };

    public static final Function fromStringAsDouble = new Function<Conversion<String, Double>, Double>() {
        public Double apply(Conversion<String, Double> conversion) {
            return Double.parseDouble(conversion.fromValue());
        }
    };

    public static final Function fromNumberAsBigDecimal = new Function<Conversion<Number, BigDecimal>, BigDecimal>() {
        public BigDecimal apply(Conversion<Number, BigDecimal> conversion) {
            return BigDecimal.valueOf(conversion.fromValue().doubleValue());
        }
    };

    public static final Function fromStringAsBigDecimal = new Function<Conversion<String, BigDecimal>, BigDecimal>() {
        public BigDecimal apply(Conversion<String, BigDecimal> conversion) {
            return BigDecimal.valueOf(Double.parseDouble(conversion.fromValue()));
        }
    };

    public static final Function fromNumberAsBigInteger = new Function<Conversion<Number, BigInteger>, BigInteger>() {
        public BigInteger apply(Conversion<Number, BigInteger> conversion) {
            return UnsignedInteger.valueOf(conversion.fromValue().longValue()).bigIntegerValue();
        }
    };

    public static final Function fromStringAsBigInteger = new Function<Conversion<String, BigInteger>, BigInteger>() {
        public BigInteger apply(Conversion<String, BigInteger> conversion) {
            return UnsignedInteger.valueOf(conversion.fromValue()).bigIntegerValue();
        }
    };

    // ------------------------------------------------------------------------
    // Integer
    // ------------------------------------------------------------------------

    public static Integer asInteger(@Nullable Number n) {
        return convertAsInteger(n).withNullAsDefaultValue();
    }

    public static Converter<Number, Integer> convertAsInteger(@Nullable Number n) {
        return transform(n, ToNumber.fromNumberAsInteger);
    }

    public static Integer asInteger(@Nullable String s) {
        return convertAsInteger(s).withNullAsDefaultValue();
    }

    public static Converter<Number, Integer> convertAsInteger(@Nullable String s) {
        return transform(s, ToNumber.fromStringAsInteger);
    }

    public static <T extends Number> Integer asInteger(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsInteger(jaxbElement).withNullAsDefaultValue();
    }

    public static <T extends Number> Converter<Number, Integer> convertAsInteger(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsInteger(ToValue.from(jaxbElement));
    }

    // ------------------------------------------------------------------------
    // Long
    // ------------------------------------------------------------------------

    public static Long asLong(@Nullable Number n) {
        return convertAsLong(n).withNullAsDefaultValue();
    }

    public static Converter<Number, Long> convertAsLong(@Nullable Number n) {
        return transform(n, ToNumber.fromNumberAsLong);
    }

    public static Long asLong(@Nullable String s) {
        return convertAsLong(s).withNullAsDefaultValue();
    }

    public static Converter<Number, Long> convertAsLong(@Nullable String s) {
        return transform(s, ToNumber.fromStringAsLong);
    }

    public static <T extends Number> Long asLong(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsLong(jaxbElement).withNullAsDefaultValue();
    }

    public static <T extends Number> Converter<Number, Long> convertAsLong(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsLong(ToValue.from(jaxbElement));
    }

    // ------------------------------------------------------------------------
    // Double
    // ------------------------------------------------------------------------

    public static Double asDouble(@Nullable Number n) {
        return convertAsDouble(n).withNullAsDefaultValue();
    }

    public static Converter<Number, Double> convertAsDouble(@Nullable Number n) {
        return transform(n, ToNumber.fromNumberAsDouble);
    }

    public static Double asDouble(@Nullable String s) {
        return convertAsDouble(s).withNullAsDefaultValue();
    }

    public static Converter<Number, Double> convertAsDouble(@Nullable String s) {
        return transform(s, ToNumber.fromStringAsDouble);
    }

    public static <T extends Number> Double asDouble(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsDouble(jaxbElement).withNullAsDefaultValue();
    }

    public static <T extends Number> Converter<Number, Double> convertAsDouble(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsDouble(ToValue.from(jaxbElement));
    }

    // ------------------------------------------------------------------------
    // BigDecimal
    // ------------------------------------------------------------------------

    public static BigDecimal asBigDecimal(@Nullable Number n) {
        return convertAsBigDecimal(n).withNullAsDefaultValue();
    }

    public static Converter<Number, BigDecimal> convertAsBigDecimal(@Nullable Number n) {
        return transform(n, ToNumber.fromNumberAsBigDecimal);
    }

    public static BigDecimal asBigDecimal(@Nullable String s) {
        return convertAsBigDecimal(s).withNullAsDefaultValue();
    }

    public static Converter<Number, BigDecimal> convertAsBigDecimal(@Nullable String s) {
        return transform(s, ToNumber.fromStringAsBigDecimal);
    }

    public static <T extends Number> BigDecimal asBigDecimal(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsBigDecimal(jaxbElement).withNullAsDefaultValue();
    }

    public static <T extends Number> Converter<Number, BigDecimal> convertAsBigDecimal(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsBigDecimal(ToValue.from(jaxbElement));
    }

    // ------------------------------------------------------------------------
    // BigInteger
    // ------------------------------------------------------------------------

    public static BigInteger asBigInteger(@Nullable Number n) {
        return convertAsBigInteger(n).withNullAsDefaultValue();
    }

    public static Converter<Number, BigInteger> convertAsBigInteger(@Nullable Number n) {
        return transform(n, ToNumber.fromNumberAsBigInteger);
    }

    public static BigInteger asBigInteger(@Nullable String s) {
        return convertAsBigInteger(s).withNullAsDefaultValue();
    }

    public static Converter<Number, BigInteger> convertAsBigInteger(@Nullable String s) {
        return transform(s, ToNumber.fromStringAsBigInteger);
    }

    public static <T extends Number> BigInteger asBigInteger(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsBigInteger(jaxbElement).withNullAsDefaultValue();
    }

    public static <T extends Number> Converter<Number, BigInteger> convertAsBigInteger(@Nullable JAXBElement<T> jaxbElement) {
        return convertAsBigInteger(ToValue.from(jaxbElement));
    }
}
