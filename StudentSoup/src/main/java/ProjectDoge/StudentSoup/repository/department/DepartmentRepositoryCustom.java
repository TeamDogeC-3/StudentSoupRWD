package ProjectDoge.StudentSoup.repository.department;

import ProjectDoge.StudentSoup.entity.school.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepositoryCustom {
    List<Department> findBySchool_Id(Long schoolId);
    Optional<Department> findByDepartmentNameAndSchool_SchoolName(String departmentName, String schoolName);
}
