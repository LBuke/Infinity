package me.adamtanana.core.i18n;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Translator {
    @Getter
    private static Translator translator = new Translator();
    private HashMap<Locale, LocaleMessage> locales = new HashMap<>();


    public Translator() {
        locales.put(Locale.ENGLISH, new LocaleMessage());
    }

    public String getMessage(Msg msg, Locale locale) {
        return locales.getOrDefault(locale, locales.get(Locale.ENGLISH)).getMessage(msg);
    }
}

class LocaleMessage {
    private List<String> words = new ArrayList<>();

    public String getMessage(Msg msg) {
        String message = words.get(msg.ordinal());
        return words.get(msg.ordinal()) == null ? msg.toLocale(Locale.ENGLISH) : message;
    }
}
