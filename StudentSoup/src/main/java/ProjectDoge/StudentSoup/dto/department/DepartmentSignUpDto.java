package ProjectDoge.StudentSoup.dto.department;

import ProjectDoge.StudentSoup.entity.school.Department;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentSignUpDto {

    private Long departmentId;
    private String departmentName;

    //== 생성 메서드 ==//
    public DepartmentSignUpDto(Department department){
        this.departmentId = department.getId();
        this.departmentName = department.getDepartmentName();
    }
}
