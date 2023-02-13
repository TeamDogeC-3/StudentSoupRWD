package ProjectDoge.StudentSoup.service.admin;

import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.dto.school.SchoolSearch;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.school.SchoolRepository;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminSchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolFindService schoolFindService;

    public List<School> AdminSearchSchools(SchoolSearch schoolSearch) {
        List<School> findSchool = schoolRepository.findSchoolDynamicSearch(schoolSearch);
        return  findSchool;
    }
    public SchoolFormDto AdminFindUpdateSchool(Long schoolId){
        School school = schoolFindService.findOne(schoolId);
        SchoolFormDto schoolFormDto = new SchoolFormDto();
        schoolFormDto.setSchool(school);
        return schoolFormDto;

    }
    @Transactional
    public void AdminUpdateSchool(Long schoolId, SchoolFormDto schoolFormDto){
        School school = schoolFindService.findOne(schoolId);
        school.setSchoolName(schoolFormDto.getSchoolName());
        school.setSchoolCoordinate(schoolFormDto.getSchoolCoordinate());
    }
}
