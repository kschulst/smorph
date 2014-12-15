package com.github.kschulst.smorph.converters;

import com.google.common.base.Joiner;

import javax.annotation.Nullable;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Converter configuration
 *
 * @param <F> Type to transform from
 * @param <T> Type to transform to
 *
 * @author kls
 */
public class Conversion<F, T> {
    private static final Joiner ERROR_MESSAGE_JOINER = Joiner.on(". ").skipNulls();

    private F fromValue;
    private T defaultValue;
    private String onErrorExceptionMessage;
    private String conversionReference;
    private boolean allowEmptyStrings;
    private boolean trimInputStrings;

    public Conversion(F fromValue) {
        this.fromValue = fromValue;
    }

    // Package private methods so that only the Converter can reach

    void setFromValue(F fromValue) {
        this.fromValue = fromValue;
    }

    void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    void setOnErrorExceptionMessage(String onErrorExceptionMessage, String conversionReference) {
        this.onErrorExceptionMessage = ERROR_MESSAGE_JOINER.join(
                (isNullOrEmpty(conversionReference) ? "" : conversionReference + " - ") + "Error converting from '" + fromValue + "'",
                emptyToNull(onErrorExceptionMessage)
        );
        this.conversionReference = conversionReference;
    }

    boolean throwsExceptionOnError() {
        return this.onErrorExceptionMessage != null;
    }

    void setAllowEmptyStrings(boolean allowEmptyStrings) {
        this.allowEmptyStrings = allowEmptyStrings;
    }

    boolean allowEmptyStrings() {
        return this.allowEmptyStrings;
    }

    void setTrimInputStrings(boolean trimInputStrings) {
        this.trimInputStrings = trimInputStrings;
    }

    boolean trimsInputStrings() {
        return this.trimInputStrings;
    }

    // ------------------------------------------------------------------------

    /**
     * Returns the value to be converted
     */
    public F fromValue() {
        return fromValue;
    }

    /**
     * Cancels the conversion and applies any default value or action if this
     * has been registered. This method should typically be invoked if the
     * conversion fails or the fromValue is invalid or null.
     */
    public T resolveDefault() {
        return resolveDefault(null, null);
    }

    /**
     * Cancels the conversion and applies any default value or action if this
     * has been registered. This method should typically be invoked if the
     * conversion fails or the fromValue is invalid or null.
     *
     * @param description Only applicable when the conversion is configured to
     *                    throw exception on errors or null-input. The message
     *                    is appended to any exception message.
     */
    public T resolveDefault(String description) {
        return resolveDefault(description, null);
    }

    /**
     * Cancels the conversion and applies any default value or action if this
     * has been registered. This method should typically be invoked if the
     * conversion fails or the fromValue is invalid or null.
     * <p>
     * If an exception is provided then this is appended to any
     * exception message and used as underlying cause for the conversion
     * exception. This only applies when the conversion is configured
     * to throw exception on errors.
     *
     * @param e Only applicable when the conversion is configured to throw
     *          exception on errors or null-input. The exception's message is
     *          appended to any exception message. Also the exception is used
     *          as underlying cause for the conversion exception that is thrown
     */
    public T resolveDefault(Exception e) {
        return resolveDefault(null, e);
    }

    /**
     * Cancels the conversion and applies any default value or action if this
     * has been registered. This method should typically be invoked if the
     * conversion fails or the fromValue is invalid or null.
     *
     * @param description Only applicable when the conversion is configured to
     *                    throw exception on errors or null-input. The message
     *                    is appended to any exception message.
     * @param e Only applicable when the conversion is configured to throw
     *          exception on errors or null-input. The exception's message is
     *          appended to any exception message. Also the exception is used
     *          as underlying cause for the conversion exception that is thrown
     */
    public T resolveDefault(@Nullable String description, @Nullable Exception e) {
        if (throwsExceptionOnError()) {
            throw (e == null)
                ? new ConversionException(ERROR_MESSAGE_JOINER.join(onErrorExceptionMessage, emptyToNull(description)), conversionReference)
                : new ConversionException(ERROR_MESSAGE_JOINER.join(onErrorExceptionMessage, emptyToNull(description), emptyToNull(e.getMessage())), e, conversionReference);
        }
        return defaultValue;
    }
}
