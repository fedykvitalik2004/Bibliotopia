package org.vitalii.fedyk.bibliotopia.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    public static String getMessage(final String messageKey, final Locale locale) {
        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }
}
