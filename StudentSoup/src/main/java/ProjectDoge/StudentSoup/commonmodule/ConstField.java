package ProjectDoge.StudentSoup.commonmodule;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConstField {

    public static final boolean LIKED = true;
    public static final boolean NOT_LIKED = false;

    public static final LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
    public static final LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
}
