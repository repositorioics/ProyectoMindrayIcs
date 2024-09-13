package ni.ics.mindrayics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static Date StringToDate(String fecha, String formato, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formato, locale);
        return sdf.parse(fecha);
    }

    public static String DateToString(Date fecha, String formato, Locale locale){
        SimpleDateFormat sdf = new SimpleDateFormat(formato, locale);
        return sdf.format(fecha);
    }
}
