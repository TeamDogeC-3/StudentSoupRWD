package ProjectDoge.StudentSoup.dto.department;

import ProjectDoge.StudentSoup.entity.school.Department;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class DepartmentFormDto {
    private Long schoolId;
    @NotEmpty(message = "학과 입력은 필수입니다")
    private String departmentName;

    //== 생성 메서드 ==//
    public void createDepartmentFormDto(Long schoolId, String departmentName){
        this.schoolId = schoolId;
        this.departmentName = departmentName;
    }
}
