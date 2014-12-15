package com.github.kschulst.smorph.converters.nullsafe;

import com.google.common.base.Function;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToValue {
    private ToValue() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static <T> Function fromJaxbElementToValue() {
        return new Function<Conversion<JAXBElement<T>, T>, T>() {
            public T apply(Conversion<JAXBElement<T>, T> conversion) {
                return conversion.fromValue().getValue();
            }
        };
    }

    // ------------------------------------------------------------------------
    // Converters
    // ------------------------------------------------------------------------

    public static <T> T from(@Nullable JAXBElement<T> jaxbElement) {
        return convert(jaxbElement).withNullAsDefaultValue();
    }

    public static <T> Converter<T, T> convert(JAXBElement<T> fromValue) {
        return transform(fromValue, fromJaxbElementToValue());
    }

}
