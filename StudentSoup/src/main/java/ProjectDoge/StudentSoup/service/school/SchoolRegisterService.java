package ProjectDoge.StudentSoup.service.school;


import ProjectDoge.StudentSoup.dto.school.SchoolFormDto;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.repository.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolRegisterService {

    private final SchoolRepository schoolRepository;
    private final SchoolValidationService schoolValidationService;

    @Transactional
    public Long join(SchoolFormDto schoolFormDto){
        log.info("학교 생성 메서드가 실행되었습니다.");
        School school = new School().createSchool(schoolFormDto);
        schoolValidationService.validateDuplicateSchool(school);
        schoolRepository.save(school);
        log.info("학교가 생성되었습니다. [{}][{}] ",school.getId(), school.getSchoolName());
        return school.getId();
    }
}
