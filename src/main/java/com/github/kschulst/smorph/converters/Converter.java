package com.github.kschulst.smorph.converters;

import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * A {@code Converter} is used to transform a value of type F to a value of type T.
 * Converters supports specifying default values or exception handling
 * strategies to be applied if the conversion fails.
 * <p>
 * The Converter is configured via a {@link Conversion} instance and performs its
 * conversion using a {@link Function} to transform the {@code fromValue}.
 * The Function is guaranteed to always retrieve a non-null {@code fromValue} when
 * invoked from the Converter.
 * <p>
 * A word of warning: Using the converter architecture implies that for every conversion
 * made, an object is instantiated. This is okay for most situations, however pay attention
 * to performance if using Converters to convert MEGA-collections
 *
 * @param <F> Type to transform from
 * @param <T> Type to transform to
 *
 * @author kls
 */
public class Converter<F, T> implements Function<F,T> {

    private final Conversion<F, T> conv;
    private final Function<Conversion<F, T>, T> function;

    public Converter(F fromValue, Function<Conversion<F, T>, T> function) {
        this.conv = new Conversion(fromValue);
        this.function = function;
    }

    /**
     * Only applicable if {code fromType} is String. Specifies that empty
     * strings are allowed and will be passed on to the conversion function.
     * By default empty strings are treated as invalid input (same as null)
     * and a ConversionException is thrown before it is applied to the
     * transform function.
     */
    public Converter<F, T> allowEmptyInput() {
        this.conv.setAllowEmptyStrings(true);
        return this;
    }

    /**
     * Only applicable if {code fromType} is String. Specifies that input
     * strings will be trimmed (leading and trailing whitespace omitted) before
     * being applied to the transform function.
     */
    public Converter<F, T> trimInput() {
        this.conv.setTrimInputStrings(true);
        return this;
    }

    /**
     * Specifies that null is to be returned in the case of invalid input or
     * an error during conversion. Finalizes the conversion configuration chain
     * and triggers the transform. This is a convenience method equal to
     * invoking {#withDefaultValue(null)}
     */
    public T withNullAsDefaultValue() {
        return withDefaultValue(null);
    }

    /**
     * Registers a default value to be returned in the case of invalid input or
     * an error during conversion. Finalizes the conversion configuration chain
     * and triggers the transform.
     */
    public T withDefaultValue(T defaultValue) {
        conv.setDefaultValue(defaultValue);
        return convert();
    }

    /**
     * Registers a message for a ConversionException to be thrown in the case
     * of error during conversion. Finalizes the conversion configuration
     * chain and triggers the transform.
     * <p>
     * The conversionReference can typically be the field name or similar
     * that can potentially later can be used to determine which conversion
     * that went wrong e.g. where you wrap multiple conversions in the same
     * try/catch.
     *
     * @param message exception message
     * @param conversionReference field name or similar that can potentially
     *                            later can be used to determine which
     *                            conversion that went wrong
     */
    public T orThrowException(String message, String conversionReference) {
        conv.setOnErrorExceptionMessage(message, conversionReference);
        return convert();
    }

    /**
     * Registers that a ConversionException should be thrown in the case
     * of error during conversion. Finalizes the conversion configuration
     * chain and triggers the transform.
     * <p>
     * The conversionReference can typically be the field name or similar
     * that can potentially later can be used to determine which conversion
     * that went wrong e.g. where you wrap multiple conversions in the same
     * try/catch.
     *
     * @param conversionReference field name or similar that can potentially
     *                            later can be used to determine which
     *                            conversion that went wrong
     */
    public T orThrowException(String conversionReference) {
        conv.setOnErrorExceptionMessage("", conversionReference);
        return convert();
    }

    /**
     * Registers that a ConversionException should be thrown in the case
     * of error during conversion. Finalizes the conversion configuration
     * chain and triggers the transform.
     */
    public T orThrowException() {
        conv.setOnErrorExceptionMessage("", null);
        return convert();
    }

    /**
     * Invokes the conversion function. Performs sanity check on "fromValue"
     * and also handles any exception that might occur inside the conversion
     * function.
     */
    private T convert() {
        // This way we guarantee that null is never passed to the conversion function
        if (conv.fromValue() == null) {
            return conv.resolveDefault("Null is not allowed");
        }

        // Handle trimming and empty strings if input is String
        if (conv.fromValue() instanceof CharSequence) {
            CharSequence fromValue = (CharSequence) conv.fromValue();

            if (conv.trimsInputStrings()) {
                fromValue = fromValue.toString().trim();
                conv.setFromValue((F) fromValue);
            }

            if (! conv.allowEmptyStrings() && fromValue.length() == 0) {
                return conv.resolveDefault("Empty strings not allowed");
            }
        }

        try {
            return function.apply(conv);
        }
        // Rethrow ConversionExceptions that might have been started within the function (e.g. due to resolveDefault())
        catch (ConversionException e) { // NOSONAR
            throw e;
        }
        catch (Exception e) {
            return conv.resolveDefault(e);
        }
    }

    public T apply(@Nullable F fromValue) {
        conv.setFromValue(fromValue);
        return convert();
    }

    /**
     * Factory method for convenience. If statically imported and given a
     * cleverly named transform function, allows for stuff like:
     * Date d = transform("2012-01-01", ToDate.fromString).withNullAsDefaultValue();
     */
    public static <F, T> Converter<F, T> transform(F fromValue, Function<Conversion<F, T>, T> function) {
        return new Converter<F, T>(fromValue, function);
    }

}
