package com.github.kschulst.smorph.converters.nullsafe;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public final class Fixtures {
    private Fixtures() {}

    public static <T> JAXBElement<T> jaxbElement(Class<T> clazz, T value) {
        return new JAXBElement<T>(new QName("http://dummy/schema/url", "somename"), clazz, clazz, value);
    }
}
