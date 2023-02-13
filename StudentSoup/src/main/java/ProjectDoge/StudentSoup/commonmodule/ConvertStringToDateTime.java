package ProjectDoge.StudentSoup.commonmodule;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConvertStringToDateTime {

    public static LocalTime convertStringToDateTime(LocalTime time){

        return LocalTime.parse(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
