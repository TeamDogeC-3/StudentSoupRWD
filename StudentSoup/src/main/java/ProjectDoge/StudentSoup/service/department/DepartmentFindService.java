package ProjectDoge.StudentSoup.service.department;

import ProjectDoge.StudentSoup.dto.department.DepartmentCallDto;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.exception.department.DepartmentIdNotSentException;
import ProjectDoge.StudentSoup.exception.department.DepartmentNotFoundException;
import ProjectDoge.StudentSoup.repository.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentFindService {

    private final DepartmentRepository departmentRepository;

    public Department findOne(Long departmentId){
        checkDepartmentIdSent(departmentId);

        return departmentRepository.findById(departmentId).orElseThrow(() -> {
            log.info("findOne 메소드가 실행되었습니다. [{}]", departmentId);
            throw new DepartmentNotFoundException("학과를 조회하지 못했습니다.");
        });
    }
    private void checkDepartmentIdSent(Long departmentId) {
        if(departmentId == null){
            log.info("departmentId가 전송되지 않았습니다.");
            throw new DepartmentIdNotSentException("departmentId가 전송되지 않았습니다.");
        }
    }

    public Department findOneUsingDepartmentNameAndSchoolName(String departmentName, String schoolName){
        Department department = departmentRepository.findByDepartmentNameAndSchool_SchoolName(departmentName, schoolName)
                .orElseThrow(() -> {
                    log.info("학교에 입력하신 학과가 존재하지 않습니다.");
                    throw new DepartmentNotFoundException("학과가 존재하지 않습니다");
                });
        return department;
    }

    public List<Department> getAllDepartmentUsingSchool(Long schoolId){
        if(schoolId == null){
            return Collections.emptyList();
        }
        List<Department> departments = departmentRepository.findBySchool_Id(schoolId);

        if(departments.isEmpty()){
            log.info("등록된 학과가 없는 예외가 발생했습니다.");
            throw new DepartmentNotFoundException("학과가 존재하지 않습니다.");
        }
        return departments;
    }

    public List<DepartmentCallDto> getDepartmentBySchoolId(Long schoolId){
        if(schoolId == null){
            return Collections.emptyList();
        }
        List<Department> departments = departmentRepository.findBySchool_Id(schoolId);
        List<DepartmentCallDto> departmentCallDtoList = new ArrayList<>();
        if(departments.isEmpty()){
            log.info("등록된 학과가 없는 예외가 발생했습니다.");
            throw new DepartmentNotFoundException("학과가 존재하지 않습니다.");
        }
        for(Department department : departments){
            departmentCallDtoList.add(new DepartmentCallDto(department));
        }
        return departmentCallDtoList;
    }

}
