package org.vitalii.fedyk.bibliotopia.constant;

public class RegExpConstants {
    public final static String EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public final static String ONE_DIGIT = ".*\\d.*";
    public static final String LOWER_CASE = ".*[a-z].*";
    public static final String UPPER_CASE = ".*[A-Z].*";
    public static final String SPECIAL_CHAR = ".*[@#$%^&+=].*";
    public static final String ONLY_LETTERS = "^[a-zA-Z]+$";
}
