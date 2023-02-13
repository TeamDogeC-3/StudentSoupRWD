package ProjectDoge.StudentSoup.controller.admin;

import ProjectDoge.StudentSoup.dto.admin.AdminMemberForm;
import ProjectDoge.StudentSoup.dto.admin.AdminMemberUpdateForm;
import ProjectDoge.StudentSoup.dto.department.DepartmentSignUpDto;
import ProjectDoge.StudentSoup.dto.member.MemberFormBDto;
import ProjectDoge.StudentSoup.dto.member.MemberSearch;
import ProjectDoge.StudentSoup.entity.member.GenderType;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import ProjectDoge.StudentSoup.service.admin.AdminMemberService;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import ProjectDoge.StudentSoup.service.member.MemberDeleteService;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import ProjectDoge.StudentSoup.service.member.MemberRegisterService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminMemberController {
    private final MemberRepository memberRepository;
    private final MemberFindService memberFindService;
    private final MemberRegisterService memberRegisterService;
    private final AdminMemberService adminMemberService;
    private final MemberDeleteService memberDeleteService;
    private final SchoolFindService schoolFindService;
    private final DepartmentFindService departmentFindService;

    @GetMapping()
    public String adminPage(){
        return "admin/admin-index";
    }

    @GetMapping("/member/new")
    public String createMemberForm(Model model){


        model.addAttribute("memberForm", new AdminMemberForm());
        model.addAttribute("genderTypes", GenderType.values());
        List<School> schools = schoolFindService.findAll();
        model.addAttribute("schools", schools);

        return "/admin/member/createMember";
    }

    @PostMapping("/member/new")
    public String createMemberForm(@ModelAttribute("memberForm") MemberFormBDto memberForm){
        memberRegisterService.join(memberForm);
        return "redirect:/admin";
    }

    @GetMapping("/member/edit/{memberId}")
    public String updateMemberForm(@PathVariable Long memberId, Model model){
        log.info("updateMemberForm 호출");
        Member member = memberRepository.updateFindById(memberId)
                .orElse(null);
        log.info("업데이트 용 회원 정보가 호출되었습니다. [{}]", member.getMemberId());
        AdminMemberUpdateForm memberForm = new AdminMemberUpdateForm().createMemberUpdateForm(member);
        model.addAttribute("memberForm", memberForm);
        log.info("업데이트 용 회원 정보 : [{}]", memberForm.toString());
        log.info("updateMemberForm : [{}]", memberForm);
        return "/admin/member/updateMember";
    }

    @PostMapping("/member/edit/{memberId}")
    public String updateMember(@PathVariable Long memberId, @ModelAttribute("memberForm") AdminMemberUpdateForm updateForm){
        log.info("회원 업데이트가 시작되었습니다.");
        log.info("updateForm 전달 객체 : {}", updateForm.toString());
        log.info("MultipartFile : [{}]", updateForm.getMultipartFile());
        Long updateId = adminMemberService.adminMemberUpdate(updateForm, updateForm.getMultipartFile());

        Member member = memberFindService.findOne(updateId);
        log.info("updated member password : [{}]", member.getPwd());

        return "redirect:/admin/members";
    }

    @PostMapping("/member/ajax")
    @ResponseBody
    public List<DepartmentSignUpDto> getDepartment(@RequestBody Map<String, Long> param){
        Long schoolId = param.get("schoolId");
        log.info("member/ajax , schoolId : [{}]", schoolId);
        List<Department> Department = departmentFindService.getAllDepartmentUsingSchool(schoolId);
        List<DepartmentSignUpDto> dto = Department.stream()
                .map(department -> new DepartmentSignUpDto(department))
                .collect(Collectors.toList());
        return dto;
    }
    @GetMapping("members")
    public String getMembers(@RequestParam(required = false) String field,@RequestParam(required = false) String value,Model model){

        List<Member> member = memberRepository.findAll();
        List<Member> findMember = adminMemberService.searchMember(field,value);
        model.addAttribute("members",member);
        model.addAttribute("findMembers",findMember);
        model.addAttribute("memberSearch",new MemberSearch());
        return "/admin/member/memberList";
    }

    @GetMapping("/member/delete/{memberId}")
    public String deleteMember(@PathVariable Long memberId){
        memberDeleteService.deleteMember(memberId);
        return "redirect:/admin/members";
    }



}
