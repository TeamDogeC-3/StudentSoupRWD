package ProjectDoge.StudentSoup.controller;

import ProjectDoge.StudentSoup.dto.school.SchoolIndexDto;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Index {
    private final SchoolFindService schoolFindService;

    @GetMapping("/home")
    public List<SchoolIndexDto> homeController(){
        log.info("homeController가 호출되었습니다.");

        List<School> schools = schoolFindService.findAll();
        List<SchoolIndexDto> result = schools.stream()
                .map(o -> new SchoolIndexDto(o))
                .collect(toList());

        log.info("불러온 학교 목록 DTO : {}", result);
        return result;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
