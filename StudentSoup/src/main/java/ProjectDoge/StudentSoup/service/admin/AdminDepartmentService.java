package ProjectDoge.StudentSoup.service.admin;

import ProjectDoge.StudentSoup.dto.department.DepartmentUpdateDto;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.repository.department.DepartmentRepository;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminDepartmentService {
    private final DepartmentFindService departmentFindService;


    public DepartmentUpdateDto adminFindDepartment(Long departmentId){
        Department department = departmentFindService.findOne(departmentId);
        DepartmentUpdateDto departmentUpdateDto = new DepartmentUpdateDto();
        departmentUpdateDto.createDepartmentFormDto(department.getSchool(), department.getDepartmentName());

        return departmentUpdateDto;
    }
    @Transactional
    public Long adminUpdateDepartment(Long departmentId, DepartmentUpdateDto departmentUpdateDto) {
        Department department = departmentFindService.findOne(departmentId);
        department.setDepartmentName(departmentUpdateDto.getDepartmentName());
        return department.getId();
    }
}
