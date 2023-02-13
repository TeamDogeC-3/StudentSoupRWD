package ProjectDoge.StudentSoup.repository.school;

import ProjectDoge.StudentSoup.dto.school.SchoolSearch;
import ProjectDoge.StudentSoup.entity.school.School;

import java.util.List;
import java.util.Optional;

public interface SchoolRepositoryCustom {
    Optional<School> findBySchoolName(String schoolName);

    List<School> findSchoolDynamicSearch(SchoolSearch schoolSearch);

}
