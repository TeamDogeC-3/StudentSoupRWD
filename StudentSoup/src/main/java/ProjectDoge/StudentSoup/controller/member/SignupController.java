package ProjectDoge.StudentSoup.controller.member;

import ProjectDoge.StudentSoup.dto.department.DepartmentSignUpDto;
import ProjectDoge.StudentSoup.dto.member.MemberDto;
import ProjectDoge.StudentSoup.dto.member.MemberFormADto;
import ProjectDoge.StudentSoup.dto.member.MemberFormBDto;
import ProjectDoge.StudentSoup.dto.school.SchoolSignUpDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import ProjectDoge.StudentSoup.service.member.MemberRegisterService;
import ProjectDoge.StudentSoup.service.member.MemberValidationService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class SignupController {

    private final MemberFindService memberFindService;
    private final MemberValidationService memberValidationService;
    private final MemberRegisterService memberRegisterService;
    private final SchoolFindService schoolFindService;
    private final DepartmentFindService departmentFindService;

    @PostMapping("/signUp/2")
    public MemberFormADto signUpCheck(@RequestBody MemberFormADto dto){
        log.info("signUpCheck가 호출되었습니다. ID : [{}]", dto.getId());
        memberValidationService.validateDuplicateMemberId(dto.getId());
        return dto;
    }

    @GetMapping("/signUp/3")
    public List<SchoolSignUpDto> signUpSchoolList(){
        log.info("signUpSchoolList 가 호출되었습니다.");
        List<School> schools = schoolFindService.findAll();
        List<SchoolSignUpDto> result = schools.stream()
                .map(school -> new SchoolSignUpDto(school))
                .collect(Collectors.toList());
        log.info("불러온 학교 목록 DTO : [{}]", result);
        return result;
    }

    @PostMapping("/signUp/3/{schoolId}")
    public List<DepartmentSignUpDto> signUpDepartmentList(@PathVariable Long schoolId){
        log.info("signUpDepartmentList 메소드가 실행되었습니다. schoolId : [{}]", schoolId);

        List<Department> departments = departmentFindService.getAllDepartmentUsingSchool(schoolId);
        List<DepartmentSignUpDto> result = departments.stream()
                .map(department -> new DepartmentSignUpDto(department))
                .collect(Collectors.toList());

        log.info("불러온 학과 목록 DTO : [{}]", result);
        return result;
    }

    @PostMapping("/signUp/3")
    public ResponseEntity<ConcurrentHashMap<String, String>> signUp(@RequestBody MemberFormBDto dto){
        log.info("signUp 메소드가 실행되었습니다. schoolId : [{}], departmentId : [{}]", dto.getSchoolId(), dto.getDepartmentId());
        Long memberId = memberRegisterService.join(dto);
        Member member = memberFindService.findOne(memberId);
        log.info("member 의 성별 : [{}]", member.getGender());
        ConcurrentHashMap<String, String> result = new ConcurrentHashMap<>();
        result.put("result", "ok");
        return ResponseEntity.ok(result);
    }
}
