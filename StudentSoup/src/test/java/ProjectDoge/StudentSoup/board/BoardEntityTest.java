package ProjectDoge.StudentSoup.board;

import ProjectDoge.StudentSoup.dto.board.BoardFormDto;
import ProjectDoge.StudentSoup.dto.department.DepartmentFormDto;
import ProjectDoge.StudentSoup.dto.member.MemberFormBDto;
import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.board.BoardCategory;
import ProjectDoge.StudentSoup.entity.member.GenderType;
import ProjectDoge.StudentSoup.service.board.BoardFindService;
import ProjectDoge.StudentSoup.service.board.BoardResisterService;
import ProjectDoge.StudentSoup.service.department.DepartmentRegisterService;
import ProjectDoge.StudentSoup.service.member.MemberRegisterService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class BoardEntityTest {


    @PersistenceContext
    EntityManager em;

    @Autowired
    SchoolRegisterService schoolRegisterService;
    @Autowired
    DepartmentRegisterService departmentRegisterService;

    @Autowired
    MemberRegisterService memberRegisterService;
    @Autowired
    BoardResisterService boardResisterService;

    @Autowired
    BoardFindService boardFindService;


    Long schoolId = 0L;
    Long departmentId = 0L;

    Long memberId = 0L;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMultipartFile multipartFile = new MockMultipartFile("mockFile",
            "",
            "",
            "".getBytes());


    @BeforeEach
    void schoolAndDepartmentAndMember() throws Exception {
        SchoolFormDto schoolDto = createSchool();
        schoolId = schoolRegisterService.join(schoolDto);
        DepartmentFormDto departmentDto = createDepartmentDto(schoolId);
        departmentId = departmentRegisterService.join(schoolId, departmentDto);
        MemberFormBDto memberDto = createMember();
        memberId = memberRegisterService.join(memberDto);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/uri/send")
                .file(multipartFile));
    }

    @Test
    void 게시글_생성(){
        //given
        BoardFormDto boardDto = createBoardDto("테스트 제목", BoardCategory.FREE,"테스트 내용");

        //when
        Long boardId = boardResisterService.join(memberId, boardDto, multipartFile);

        //then
        assertThat(boardId).isEqualTo(boardFindService.findOne(boardId).getId());
        assertThat(schoolId).isEqualTo(boardFindService.findOne(boardId).getSchool().getId());
        assertThat(departmentId).isEqualTo(boardFindService.findOne(boardId).getDepartment().getId());
        assertThat(memberId).isEqualTo(boardFindService.findOne(boardId).getMember().getMemberId());
    }

    private BoardFormDto createBoardDto(String title, BoardCategory category, String content) {
        BoardFormDto boardFormDto = new BoardFormDto();
        boardFormDto.setTitle(title);
        boardFormDto.setBoardCategory(category);
        boardFormDto.setContent(content);

        return boardFormDto;
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
    private MemberFormBDto createMember() {
        MemberFormBDto form = new MemberFormBDto();
        form.setId("testId");
        form.setPwd("testPassword");
        form.setNickname("테스트닉네임");
        form.setEmail("test@naver.com");
        form.setGender(GenderType.MAN);
        form.setSchoolId(schoolId);
        form.setDepartmentId(departmentId);
        return form;
    }
}
