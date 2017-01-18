package me.adamtanana.core.i18n;

import java.util.Locale;

public enum Msg {
    EMPTY(""),;

    private String english;

    Msg(String english) {
        this.english = english;
    }

    public String toLocale(Locale locale) {
        return Translator.getTranslator().getMessage(this, locale);
    }

}
