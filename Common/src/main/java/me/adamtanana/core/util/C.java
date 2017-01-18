package me.adamtanana.core.util;

import java.util.regex.Pattern;

public class C {
    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");

    public static final String bold = COLOR_CHAR + "l";
    public static final String strike = COLOR_CHAR + "m";
    public static final String underline = COLOR_CHAR + "n";
    public static final String magic = COLOR_CHAR + "k";
    public static final String italic = COLOR_CHAR + "o";
    public static final String reset = COLOR_CHAR + "r";

    public static final String black = COLOR_CHAR + "0";

    public static final String darkBlue = COLOR_CHAR + "1";

    public static final String darkGreen = COLOR_CHAR + "2";

    public static final String darkAqua = COLOR_CHAR + "3";

    public static final String darkRed = COLOR_CHAR + "4";

    public static final String darkPurple = COLOR_CHAR + "5";

    public static final String gold = COLOR_CHAR + "6";

    public static final String gray = COLOR_CHAR + "7";

    public static final String darkGray = COLOR_CHAR + "8";

    public static final String blue = COLOR_CHAR + "9";

    public static final String green = COLOR_CHAR + "a";

    public static final String aqua = COLOR_CHAR + "b";

    public static final String red = COLOR_CHAR + "c";

    public static final String lightPurple = COLOR_CHAR + "d";

    public static final String yellow = COLOR_CHAR + "e";

    public static final String white = COLOR_CHAR + "f";


    public static String stripColor(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String replaceColors(String colorless) {
        colorless = colorless.replaceAll("&([0-9]|[abcdefkrlmnoABCDEFKRLMNO])", COLOR_CHAR + "$1");
        return colorless;
    }


}
