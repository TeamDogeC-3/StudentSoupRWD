package ProjectDoge.StudentSoup.dto.department;

import ProjectDoge.StudentSoup.entity.school.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentCallDto {

    private Long departmentId;

    private String departmentName;


    public DepartmentCallDto(Department department) {
        this.departmentId = department.getId();
        this.departmentName = department.getDepartmentName();
    }
}
