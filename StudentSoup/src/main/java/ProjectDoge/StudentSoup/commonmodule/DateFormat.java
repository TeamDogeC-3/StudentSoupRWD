package ProjectDoge.StudentSoup.commonmodule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {

    public static String dateFormat(String date){
        java.text.DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date beforeDate = new Date();
        try {
            beforeDate = dateFormat.parse(date);
        } catch(ParseException e){
            e.getMessage();
        }
        String afterDate = sdf.format(beforeDate);
        return afterDate;
    }
}
