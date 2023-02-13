package ProjectDoge.StudentSoup.service.department;

import ProjectDoge.StudentSoup.dto.department.DepartmentFormDto;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.department.DepartmentRepository;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import ProjectDoge.StudentSoup.service.school.SchoolValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentRegisterService {

    private final DepartmentRepository departmentRepository;
    private final SchoolFindService schoolFindService;
    private final DepartmentValidationService departmentValidationService;

    @Transactional
    public Long join(Long schoolId, DepartmentFormDto dto){
        log.info("학과 생성 메서드가 실행되었습니다.");
        School school = schoolFindService.findOne(schoolId);
        Department department = new Department().createDepartmentForm(dto, school);
        departmentValidationService.validateDuplicateDepartment(department);
        departmentRepository.save(department);
        log.info("학과가 생성되었습니다. [{}][{}] ",department.getId(), department.getDepartmentName());
        return department.getId();
    }

}
