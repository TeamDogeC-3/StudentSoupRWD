package ProjectDoge.StudentSoup.member;

import ProjectDoge.StudentSoup.dto.department.DepartmentFormDto;
import ProjectDoge.StudentSoup.dto.member.MemberDto;
import ProjectDoge.StudentSoup.dto.member.MemberFormADto;
import ProjectDoge.StudentSoup.dto.member.MemberFormBDto;
import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.member.GenderType;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.exception.member.MemberNotMatchIdPwdException;
import ProjectDoge.StudentSoup.service.department.DepartmentRegisterService;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import ProjectDoge.StudentSoup.service.member.MemberLoginService;
import ProjectDoge.StudentSoup.service.member.MemberRegisterService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@Rollback
public class MemberLoginTest {

    @Autowired
    MemberRegisterService memberRegisterService;

    @Autowired
    MemberLoginService memberLoginService;

    @Autowired
    MemberFindService memberFindService;
    @Autowired
    SchoolRegisterService schoolRegisterService;
    @Autowired
    DepartmentRegisterService departmentRegisterService;

    Long schoolId = 0L;
    Long departmentId = 0L;
    @BeforeEach
    void schoolAndDepartment() {
        SchoolFormDto schoolFormDto = createSchool();
        schoolId = schoolRegisterService.join(schoolFormDto);
        DepartmentFormDto dto = createDepartmentDto(schoolId);
        departmentId = departmentRegisterService.join(schoolId, dto);

    }

    @Test
    void 로그인_아이디_패스워드_틀림() throws Exception {
        //given
        MemberFormADto formA1 = createMemberFormA("test1", "test123!");
        MemberFormBDto formB1 = createMemberFormB(formA1);
        formB1.setNickname("테스트1");
        formB1.setGender(GenderType.MAN);
        formB1.setSchoolId(schoolId);
        formB1.setDepartmentId(departmentId);
        formB1.setEmail("test1@naver.com");
        memberRegisterService.join(formB1);
        //when
        String id = "test1";
        String pwd = "test12!";
        //then
        assertThatThrownBy(() -> memberLoginService.login(id, pwd))
                .isInstanceOf(MemberNotMatchIdPwdException.class);
    }

    @Test
    void 로그인_성공() throws Exception {
        //given
        MemberFormADto formA1 = createMemberFormA("test1", "test123!");
        MemberFormBDto formB1 = createMemberFormB(formA1);
        formB1.setNickname("테스트1");
        formB1.setGender(GenderType.MAN);
        formB1.setSchoolId(schoolId);
        formB1.setDepartmentId(departmentId);
        formB1.setEmail("test1@naver.com");
        Long memberId = memberRegisterService.join(formB1);
        Member member = memberFindService.findOne(memberId);
        //when
        String id = "test1";
        String pwd = "test123!";
        MemberDto memberDto = memberLoginService.login(id, pwd);
        //then
        // 아이디 확인
        assertThat(memberDto.getId()).isEqualTo(member.getId());
        // 학교 확인
        assertThat(memberDto.getSchoolId()).isEqualTo(schoolId);
        // 학과 확인
        assertThat(memberDto.getDepartmentId()).isEqualTo(departmentId);
        // 파일 확인
        assertThat(memberDto.getFileName()).isEqualTo(null);
    }

    private SchoolFormDto createSchool() {

        SchoolFormDto schoolFormDto = new SchoolFormDto();
        schoolFormDto.setSchoolName("테스트 학교");
        schoolFormDto.setSchoolCoordinate("테스트 학교 좌표");
        return schoolFormDto;
    }

    private DepartmentFormDto createDepartmentDto(Long schoolId) {
        DepartmentFormDto dto = new DepartmentFormDto();
        dto.createDepartmentFormDto(schoolId, "테스트 학과");
        return dto;
    }

    private MemberFormADto createMemberFormA(String id, String pwd) {
        MemberFormADto formA = new MemberFormADto();
        formA.setId(id);
        formA.setPwd(pwd);
        return formA;
    }

    private MemberFormBDto createMemberFormB(MemberFormADto dto) {
        MemberFormBDto formB = new MemberFormBDto();
        formB.setId(dto.getId());
        formB.setPwd(dto.getPwd());
        formB.setNickname("테스트닉네임");
        formB.setEmail("test@naver.com");
        formB.setGender(GenderType.MAN);
        return formB;
    }
}
