package org.vitalii.fedyk.bibliotopia.constant;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum LocaleConstants {
    DEFAULT(Locale.forLanguageTag("en")),
    UK(Locale.forLanguageTag("uk"));

    private final Locale locale;

    public static Locale getDefaultLocale() {
        return DEFAULT.locale;
    }

    public static List<Locale> getAllLocalesExceptDefault() {
        return Arrays.stream(values())
                .map(constant -> constant.locale)
                .filter(constant -> constant != DEFAULT.locale)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
