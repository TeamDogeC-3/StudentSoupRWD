package ProjectDoge.StudentSoup.controller.admin.school;

import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.dto.school.SchoolSearch;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.school.SchoolRepository;
import ProjectDoge.StudentSoup.service.admin.AdminSchoolService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import ProjectDoge.StudentSoup.service.school.SchoolRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminSchoolController {
    private final SchoolFindService schoolFindService;
    private final SchoolRegisterService schoolRegisterService;
    private final AdminSchoolService adminSchoolService;
    private final SchoolRepository schoolRepository;

    @GetMapping("admin/school/new")
    public String createSchool(Model model){
        model.addAttribute("schoolForm",new SchoolFormDto());
        return "/admin/school/createSchool";
    }
    @PostMapping("admin/school/new")
    public String createSchool(@Valid SchoolFormDto formDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/admin/school/createSchool";
        }
        schoolRegisterService.join(formDto);
        return "redirect:/admin/schools";
    }
    @GetMapping("/admin/schools")
    public String schoolList(@ModelAttribute("schoolSearch") SchoolSearch schoolSearch, Model model) {
        List<School> schools = schoolFindService.findAll();
        model.addAttribute("schools", schools);

        List<School> findSchools = adminSchoolService.AdminSearchSchools(schoolSearch);
        model.addAttribute("findSchools", findSchools);

        return "/admin/school/schoolList";
    }
    @GetMapping("/admin/school/edit")
    public String editSchool(@RequestParam("schoolId")Long schoolId,Model model){
        SchoolFormDto updateSchool = adminSchoolService.AdminFindUpdateSchool(schoolId);
        model.addAttribute("schoolId",schoolId);
        model.addAttribute("schoolForm",updateSchool);
        return "/admin/school/updateSchool";
    }
    @PostMapping("/admin/school/edit")
    public String editSchool(@RequestParam("schoolId")Long schoolId,@ModelAttribute("form") SchoolFormDto schoolFormDto){
        adminSchoolService.AdminUpdateSchool(schoolId,schoolFormDto);
        return "redirect:/admin/schools";
    }
    @GetMapping("/admin/school/delete")
    public String deleteSchool(@RequestParam("schoolId")Long schoolId){
        schoolRepository.deleteById(schoolId);
        return "redirect:/admin/schools";
    }


}
