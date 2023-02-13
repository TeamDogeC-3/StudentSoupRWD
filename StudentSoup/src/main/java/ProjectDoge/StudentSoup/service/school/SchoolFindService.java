package ProjectDoge.StudentSoup.service.school;

import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.exception.school.SchoolIdNotSentException;
import ProjectDoge.StudentSoup.exception.school.SchoolNameNotSentException;
import ProjectDoge.StudentSoup.exception.school.SchoolNotFoundException;
import ProjectDoge.StudentSoup.repository.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolFindService {

    private final SchoolRepository schoolRepository;

    public School findOne(Long schoolId){
        checkSchoolIdSent(schoolId);

        return schoolRepository.findById(schoolId).orElseThrow(() -> {
            log.info("findOne 메소드가 실행되었습니다. [{}]", schoolId);
            throw new SchoolNotFoundException("학교를 찾지 못하였습니다.");
        });
    }

    public School findOne(String schoolName){

        if(schoolName == null){
            log.info("[{}]을 호출하였으나 파라미터 : schoolName 이 전달되지 않았습니다.", this.getClass().getName());
            throw new SchoolNameNotSentException("schoolName 이 전달되지 않았습니다. 올바른 접근이 필요합니다.");
        }

        return schoolRepository.findBySchoolName(schoolName).orElseThrow(() -> {
            log.info("findOne 메소드가 실행되었습니다. [{}]", schoolName);
            throw new SchoolNotFoundException("학교를 찾지 못하였습니다.");
        });
    }

    private void checkSchoolIdSent(Long schoolId) {
        if(schoolId == null){
            log.info("schoolId가 전송되지 않았습니다.");
            throw new SchoolIdNotSentException("schoolId가 전송되지 않았습니다.");
        }
    }

    public List<School> findAll(){
        if(schoolRepository.findAll().isEmpty()){
            log.info("등록된 학교가 없는 예외가 발생했습니다.");
            throw new SchoolNotFoundException("등록된 학교가 존재하지 않습니다.");
        }
        return schoolRepository.findAll();
    }
}
