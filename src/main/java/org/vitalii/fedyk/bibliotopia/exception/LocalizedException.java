package org.vitalii.fedyk.bibliotopia.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalizedException extends RuntimeException {
    private final String messageKey;
    private final Locale locale;
    private List<String> elements = new ArrayList<>();

    public LocalizedException(final String messageKey, final Locale locale) {
        this.messageKey = messageKey;
        this.locale = locale == null ? LocaleContextHolder.getLocale() : locale;
    }

    public LocalizedException(final String messageKey) {
        this(messageKey, (Locale) null);
    }

    public LocalizedException(final String messageKey, final List<String> elements) {
        this(messageKey, (Locale) null);
        this.elements = List.copyOf(elements);
    }

    @Override
    public String getLocalizedMessage() {
        return Messages.getMessage(messageKey, locale)
                .formatted(elements.toArray());
    }
}
