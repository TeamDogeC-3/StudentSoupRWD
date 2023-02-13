package ProjectDoge.StudentSoup.school;

import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.exception.school.SchoolValidationException;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
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
public class SchoolEntityTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private SchoolRegisterService schoolRegisterService;

    @Autowired
    private SchoolFindService schoolFindService;

    @Test
    void 학교등록테스트() throws Exception {
        //given
        SchoolFormDto school = createSchool();
        //when
        Long schoolId = schoolRegisterService.join(school);
        //then
        assertThat(schoolId).isEqualTo(schoolFindService.findOne(schoolId).getId());
    }

    @Test
    void 학교중복검증테스트() throws Exception {
        //given
        SchoolFormDto school1 = createSchool();
        SchoolFormDto school2 = createSchool();
        //when
        schoolRegisterService.join(school1);
        //then
        assertThatThrownBy(()-> schoolRegisterService.join(school2))
                .isInstanceOf(SchoolValidationException.class);
    }

    private SchoolFormDto createSchool(){
        SchoolFormDto school = new SchoolFormDto();
        school.setSchoolName("테스트 학교 추가");
        school.setSchoolCoordinate("테스트 학교 좌표");

        return school;
    }
}
