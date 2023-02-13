package ProjectDoge.StudentSoup.dto.department;

import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class DepartmentUpdateDto {
    private School school;
    @NotEmpty(message = "학과 입력은 필수입니다")
    private String departmentName;

    //== 생성 메서드 ==//
    public void createDepartmentFormDto(School school, String departmentName){
        this.school = school;
        this.departmentName = departmentName;
    }

}
