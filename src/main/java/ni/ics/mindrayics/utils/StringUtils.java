package ni.ics.mindrayics.utils;

public class StringUtils {
    public static final String EMPTY_STRING = "";

    public static boolean isNullOrEmpty(String string) {
        return string==null || string.trim().equals(EMPTY_STRING);
    }
}
