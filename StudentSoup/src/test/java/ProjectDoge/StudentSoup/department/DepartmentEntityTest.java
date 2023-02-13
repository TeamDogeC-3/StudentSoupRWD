package ProjectDoge.StudentSoup.department;

import ProjectDoge.StudentSoup.dto.department.DepartmentFormDto;
import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.exception.school.SchoolNotFoundException;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import ProjectDoge.StudentSoup.service.department.DepartmentRegisterService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class DepartmentEntityTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private DepartmentRegisterService departmentRegisterService;

    @Autowired
    private DepartmentFindService departmentFindService;

    @Autowired
    private SchoolRegisterService schoolRegisterService;

    @Autowired
    private SchoolFindService schoolFindService;

    Long schoolId = 0L;

    @BeforeEach
    void 학교등록() {
        SchoolFormDto school = createSchool();
        schoolId = schoolRegisterService.join(school);
    }
    @Test
    void 학과등록테스트() throws Exception {
        //given
        DepartmentFormDto dto = createDepartmentForm(schoolId, "테스트 학과");
        //when
        Long departmentId = departmentRegisterService.join(schoolId, dto);
        School school = schoolFindService.findOne(schoolId);
        //then
        assertThat(departmentId).isEqualTo(departmentFindService.findOneUsingDepartmentNameAndSchoolName(dto.getDepartmentName(), school.getSchoolName()).getId());
    }

    @Test
    void 학과등록시_학교존재X() throws Exception {
        //given
        Long errorSchoolId = 0L;
        DepartmentFormDto dto = createDepartmentForm(errorSchoolId, "테스트 학과");
        //when

        //then
        assertThatThrownBy(() -> departmentRegisterService.join(errorSchoolId, dto))
                .isInstanceOf(SchoolNotFoundException.class);
    }

    private SchoolFormDto createSchool(){
        SchoolFormDto school = new SchoolFormDto();
        school.setSchoolName("테스트 학교");
        school.setSchoolCoordinate("테스트 학교 좌표");
        return school;
    }

    private DepartmentFormDto createDepartmentForm(Long schoolId, String departmentName){
        DepartmentFormDto dto = new DepartmentFormDto();
        dto.createDepartmentFormDto(schoolId, departmentName);
        return dto;
    }

    private Department createDepartment(String departmentName) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        return department;
    }
}
