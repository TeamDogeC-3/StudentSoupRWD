package ProjectDoge.StudentSoup.service.department;

import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.repository.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentValidationService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentFindService departmentFindService;

    public void validateDuplicateDepartment(Department department){
        log.info("학과 생성 검증 메소드가 실행되었습니다.");
        departmentRepository.findByDepartmentNameAndSchool_SchoolName(department.getDepartmentName(), department.getSchool().getSchoolName())
                .ifPresent(value -> {
                    log.info("학과가 존재하는 예외가 발생했습니다.");
                    throw new IllegalStateException("이미 존재하는 학과입니다.");
                });
        log.info("학과 검증이 완료되었습니다.");
    }
}
