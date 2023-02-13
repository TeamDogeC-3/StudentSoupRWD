package ProjectDoge.StudentSoup.dto.school;

import ProjectDoge.StudentSoup.entity.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolIndexDto {
    private Long schoolId;
    private String schoolName;

    //== 생성 메서드 ==//
    public SchoolIndexDto(School school){
        this.schoolId = school.getId();
        this.schoolName = school.getSchoolName();
    }
}
