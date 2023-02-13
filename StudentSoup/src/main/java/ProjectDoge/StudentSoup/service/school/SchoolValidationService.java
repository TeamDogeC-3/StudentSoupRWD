package ProjectDoge.StudentSoup.service.school;

import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.exception.school.SchoolValidationException;
import ProjectDoge.StudentSoup.repository.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolValidationService {

    private final SchoolRepository schoolRepository;
    public void validateDuplicateSchool(School school){
        log.info("학교 생성 검증 메소드가 실행되었습니다.");
        schoolRepository.findBySchoolName(school.getSchoolName())
                .ifPresent((value) -> {
                    log.info("학교가 존재하는 예외가 발생했습니다.");
                    throw new SchoolValidationException("이미 존재하는 학교입니다.");
                });
        log.info("학교 검증이 완료되었습니다.");
    }
}
