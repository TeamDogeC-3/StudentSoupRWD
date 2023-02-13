package ProjectDoge.StudentSoup.dto.school;

import ProjectDoge.StudentSoup.entity.school.School;
import lombok.Data;

@Data
public class SchoolResponseDto {
    private Long schoolId;
    private String schoolName;
    private String schoolLongitude;
    private String schoolLatitude;


    public SchoolResponseDto(School school){
        this.schoolId = school.getId();
        this.schoolName = school.getSchoolName();
        this.schoolLongitude = school.getSchoolCoordinate().split(",")[0];
        this.schoolLatitude = school.getSchoolCoordinate().split(",")[1];
    }
}

