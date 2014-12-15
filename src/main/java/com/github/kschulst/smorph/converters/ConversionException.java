package com.github.kschulst.smorph.converters;

public class ConversionException extends RuntimeException {

    private final String conversionReference;

    public ConversionException(String message, String conversionReference) {
        super(message);
        this.conversionReference = conversionReference;
    }

    public ConversionException(String message, Throwable throwable, String conversionReference) {
        super(message, throwable);
        this.conversionReference = conversionReference;
    }

    /**
     * This will typically be the name of the field that failed conversion. Can be used
     * to identify a specific conversion.
     */
    public String getConversionReference() {
        return conversionReference;
    }
}
