package com.github.kschulst.smorph.converters.formatters;

import com.google.common.base.Function;
import com.github.kschulst.smorph.base.NumberFormat;
import com.github.kschulst.smorph.converters.Conversion;
import com.github.kschulst.smorph.converters.Converter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.github.kschulst.smorph.converters.Converter.transform;

public final class ToNumberString {
    private ToNumberString() {}

    // ------------------------------------------------------------------------
    // Functions
    // ------------------------------------------------------------------------

    public static Function fromNumber(@Nonnull final NumberFormat format) {
        return new Function<Conversion<Number, String>, String>() {
            public String apply(Conversion<Number, String> conversion) {
                return format.getFormat().format(conversion.fromValue());
            }
        };
    }

    // ------------------------------------------------------------------------
    // Formatters
    // ------------------------------------------------------------------------

    public static Converter<Number, String> format(@Nullable Number n, @Nonnull NumberFormat format) {
        return transform(n, ToNumberString.fromNumber(format));
    }

    public static String from(@Nullable Number n, @Nonnull NumberFormat format) {
        return format(n, format).withNullAsDefaultValue();
    }

}
