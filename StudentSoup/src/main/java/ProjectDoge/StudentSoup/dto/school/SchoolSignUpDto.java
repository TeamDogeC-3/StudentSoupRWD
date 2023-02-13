package ProjectDoge.StudentSoup.dto.school;


import ProjectDoge.StudentSoup.entity.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolSignUpDto {

    private Long schoolId;
    private String schoolName;

    //== 생성 메서드 ==//
    public SchoolSignUpDto(School school){
        this.schoolId = school.getId();
        this.schoolName = school.getSchoolName();
    }
}
