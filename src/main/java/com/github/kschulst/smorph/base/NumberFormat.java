package com.github.kschulst.smorph.base;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Common formats for your convenience and pleasure
 */
public enum NumberFormat {
    /**
     * <ul>
     * <li>10000.295 -> 10000</li>
     * <li>10000 -> 10000</li>
     * </ul>
     */
    NO_DECIMALS("0", '.'),

    /**
     * <ul>
     * <li>10000.295 -> 10000,2</li>
     * <li>10000 -> 10000,0</li>
     * </ul>
     */
    N_COMMA_D("0.0", ','),

    /**
     * <ul>
     * <li>10000.295 -> 10000.2</li>
     * <li>10000 -> 10000.0</li>
     * </ul>
     */
    N_DOT_D("0.0", '.'),

    /**
     * <ul>
     * <li>10000.295 -> 10000,29</li>
     * <li>10000 -> 10000,00</li>
     * </ul>
     */
    N_COMMA_DD("0.00", ','),

    /**
     * <ul>
     * <li>10000.295 -> 10000.29</li>
     * <li>10000 -> 10000.00</li>
     * </ul>
     */
    N_DOT_DD("0.00", '.'),

    /**
     * <ul>
     * <li>10000.295 -> 10000,295</li>
     * <li>10000 -> 10000,000</li>
     * </ul>
     */
    N_COMMA_DDD("0.000", ','),

    /**
     * <ul>
     * <li>10000.295 -> 10000.295</li>
     * <li>10000 -> 10000.000</li>
     * </ul>
     */
    N_DOT_DDD("0.000", '.'),

    /**
     * <ul>
     * <li>10000.295 -> 10000,2950</li>
     * <li>10000 -> 10000,0000</li>
     * </ul>
     */
    N_COMMA_DDDD("0.0000", ','),

    /**
     * <ul>
     * <li>10000.295 -> 10000.2950</li>
     * <li>10000 -> 10000.0000</li>
     * </ul>
     */
    N_DOT_DDDD("0.0000", '.');

    private final Locale NORWEGIAN_LOCALE = new Locale("nb", "NO");
    private final DecimalFormatSymbols symbols;
    private final DecimalFormat format;
    private final String pattern;

    private NumberFormat(String pattern, char decimalSeparator) {
        this.symbols = new DecimalFormatSymbols(NORWEGIAN_LOCALE);
        this.symbols.setDecimalSeparator(decimalSeparator);
        this.format = new DecimalFormat(pattern);
        this.format.setDecimalFormatSymbols(symbols);
        this.pattern = pattern;
    }

    public DecimalFormat getFormat() {
        return format;
    }

    public String getPattern() {
        return pattern;
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        return symbols;
    }
}
