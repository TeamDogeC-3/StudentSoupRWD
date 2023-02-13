package ProjectDoge.StudentSoup.member;

import ProjectDoge.StudentSoup.dto.department.DepartmentFormDto;
import ProjectDoge.StudentSoup.dto.member.MemberFormADto;
import ProjectDoge.StudentSoup.dto.member.MemberFormBDto;
import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.member.GenderType;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.exception.member.MemberValidationException;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import ProjectDoge.StudentSoup.repository.school.SchoolRepository;
import ProjectDoge.StudentSoup.service.department.DepartmentRegisterService;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import ProjectDoge.StudentSoup.service.member.MemberRegisterService;
import ProjectDoge.StudentSoup.service.member.MemberValidationService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
@Transactional
@Rollback
public class MemberEntityTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberRegisterService memberRegisterService;

    @Autowired
    MemberValidationService memberValidationService;

    @Autowired
    MemberFindService memberFindService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SchoolRegisterService schoolRegisterService;
    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    DepartmentRegisterService departmentRegisterService;



    @Nested
    @DisplayName("회원가입 검증")
    class 회원가입 {
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
        void 회원가입_A_중복() throws Exception {
            //given
            MemberFormADto ADto = createMemberFormA("test1", "test123!");
            MemberFormBDto BDto = createMemberFormB(ADto);
            BDto.setSchoolId(schoolId);
            BDto.setDepartmentId(departmentId);

            log.info("BDto 출력 : {}", BDto.toString());

            memberRegisterService.join(BDto);
            //when
            MemberFormADto memberFormADto = createMemberFormA("test1", "test123!");
            //then
            assertThatThrownBy(() -> memberValidationService.validateDuplicateMemberId(memberFormADto.getId()))
                    .isInstanceOf(MemberValidationException.class);
        }

        @Test
        void 회원가입_A성공ToB() throws Exception {
            //given
            MemberFormADto memberFormADto = createMemberFormA("test1", "test123!");
            //when
            MemberFormBDto memberFormBDto = createMemberFormB(memberFormADto);
            memberFormBDto.setSchoolId(schoolId);
            memberFormBDto.setDepartmentId(departmentId);
            //then
            assertThat(memberFormADto.getId()).isEqualTo(memberFormBDto.getId());
            assertThat(memberFormADto.getPwd()).isEqualTo(memberFormBDto.getPwd());
        }

        @Test
        void 회원가입_성공() throws Exception {
            //given
            MemberFormADto memberFormADto = createMemberFormA("test1", "test123!");
            MemberFormBDto memberFormBDto = createMemberFormB(memberFormADto);
            memberFormBDto.setSchoolId(schoolId);
            memberFormBDto.setDepartmentId(departmentId);

            //when
            Long memberId = memberRegisterService.join(memberFormBDto);
            //then
            assertThat(memberId).isEqualTo(memberFindService.findOne(memberId).getMemberId());
            assertThat(schoolId).isEqualTo(memberFindService.findOne(memberId).getSchool().getId());
            assertThat(departmentId).isEqualTo(memberFindService.findOne(memberId).getDepartment().getId());
        }

        @Test
        void 회원가입_닉네임_중복() throws Exception {
            //given
            MemberFormADto memberFormADto1 = createMemberFormA("test", "test123!");
            MemberFormBDto memberFormBDto1 = createMemberFormB(memberFormADto1);
            memberFormBDto1.setSchoolId(schoolId);
            memberFormBDto1.setDepartmentId(departmentId);

            memberRegisterService.join(memberFormBDto1);

            MemberFormADto memberFormADto2 = createMemberFormA("test1", "test123!");
            MemberFormBDto memberFormBDto2 = createMemberFormB(memberFormADto2);
            memberFormBDto2.setEmail("test1@naver.com");
            memberFormBDto2.setSchoolId(schoolId);
            memberFormBDto2.setDepartmentId(departmentId);
            //when

            //then
            assertThatThrownBy(() -> memberValidationService.validateDuplicateMemberNickname(memberFormBDto2.getNickname()))
                    .isInstanceOf(MemberValidationException.class);
        }

        @Test
        void 회원가입_이메일_중복() throws Exception {
            //given
            MemberFormADto memberFormADto1 = createMemberFormA("test", "test123!");
            MemberFormBDto memberFormBDto1 = createMemberFormB(memberFormADto1);
            memberFormBDto1.setSchoolId(schoolId);
            memberFormBDto1.setDepartmentId(departmentId);

            memberRegisterService.join(memberFormBDto1);

            MemberFormADto memberFormADto2 = createMemberFormA("test1", "test123!");
            MemberFormBDto memberFormBDto2 = createMemberFormB(memberFormADto2);
            memberFormBDto2.setNickname("테스트닉네임2");
            memberFormBDto2.setSchoolId(schoolId);
            memberFormBDto2.setDepartmentId(departmentId);
            //when

            //then
            assertThatThrownBy(() -> memberValidationService.validateDuplicateMemberEmail(memberFormBDto2.getEmail()))
                    .isInstanceOf(MemberValidationException.class);
        }

        @Test
        void 회원가입_중복() throws Exception {
            //given
            MemberFormADto memberFormADto1 = createMemberFormA("test", "test123!");
            MemberFormBDto memberFormBDto1 = createMemberFormB(memberFormADto1);
            memberFormBDto1.setSchoolId(schoolId);
            memberFormBDto1.setDepartmentId(departmentId);

            memberRegisterService.join(memberFormBDto1);

            MemberFormADto memberFormADto2 = createMemberFormA("test1", "test123!");
            MemberFormBDto memberFormBDto2 = createMemberFormB(memberFormADto2);
            memberFormBDto2.setSchoolId(schoolId);
            memberFormBDto2.setDepartmentId(departmentId);
            //when

            //then
            assertThatThrownBy(() -> memberRegisterService.join(memberFormBDto2))
                    .isInstanceOf(MemberValidationException.class);
        }
    }
    @Nested
    @DisplayName("회원가입 이후 검증")
    class 회원가입이후{
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
        void 학과내_회원검증() throws Exception {
            //given
            MemberFormADto formA1 = createMemberFormA("test1", "test123!");
            MemberFormBDto formB1 = createMemberFormB(formA1);
            formB1.setNickname("테스트1");
            formB1.setGender(GenderType.MAN);
            formB1.setSchoolId(schoolId);
            formB1.setDepartmentId(departmentId);
            formB1.setEmail("test1@naver.com");
            Long member1Id = memberRegisterService.join(formB1);

            MemberFormADto formA2 = createMemberFormA("test2", "test123!");
            MemberFormBDto formB2 = createMemberFormB(formA2);
            formB2.setNickname("테스트2");
            formB2.setGender(GenderType.MAN);
            formB2.setSchoolId(schoolId);
            formB2.setDepartmentId(departmentId);
            formB2.setEmail("test2@naver.com");
            Long member2Id = memberRegisterService.join(formB2);
            //when
            Member member1 = memberFindService.findOne(member1Id);
            Member member2 = memberFindService.findOne(member2Id);
            List<Member> members = memberRepository.findByDepartment_Id(departmentId);
            //then
            assertThat(members.contains(member1)).isEqualTo(true);
            assertThat(members.contains(member2)).isEqualTo(true);
        }
        @Test
        void 학교내_회원검증() throws Exception {
            //given
            MemberFormADto formA1 = createMemberFormA("test1", "test123!");
            MemberFormBDto formB1 = createMemberFormB(formA1);
            formB1.setNickname("테스트1");
            formB1.setGender(GenderType.MAN);
            formB1.setSchoolId(schoolId);
            formB1.setDepartmentId(departmentId);
            formB1.setEmail("test1@naver.com");
            Long member1Id = memberRegisterService.join(formB1);

            MemberFormADto formA2 = createMemberFormA("test2", "test123!");
            MemberFormBDto formB2 = createMemberFormB(formA2);
            formB2.setNickname("테스트2");
            formB2.setGender(GenderType.MAN);
            formB2.setSchoolId(schoolId);
            formB2.setDepartmentId(departmentId);
            formB2.setEmail("test2@naver.com");
            Long member2Id = memberRegisterService.join(formB2);

            //when
            List<Member> members = memberRepository.findBySchool_SchoolId(schoolId);
            //then
            for (Member member : members) {
                log.info("memberId : {}, id : {}, nickName : {}", member.getMemberId(), member.getId(), member.getNickname());
            }
            // 회원 검증
            assertThat(members.get(0).getMemberId()).isEqualTo(member1Id);
            assertThat(members.get(1).getMemberId()).isEqualTo(member2Id);
            // 학과 검증
            log.info("학과 검증을 시작하였습니다.");
            assertThat(members.get(0).getDepartment().getId()).isEqualTo(departmentId);
            // 학교 검증
            log.info("학교 검증을 시작하였습니다.");
            assertThat(members.get(0).getSchool().getId()).isEqualTo(schoolId);
        }

    }
        private SchoolFormDto createSchool() {
            SchoolFormDto dto = new SchoolFormDto();
            dto.setSchoolName("테스트 학교");
            dto.setSchoolCoordinate("테스트 학교 좌표");
            return dto;
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
