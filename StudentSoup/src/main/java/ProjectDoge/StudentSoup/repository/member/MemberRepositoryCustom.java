package ProjectDoge.StudentSoup.repository.member;

import ProjectDoge.StudentSoup.dto.member.MemberFindAccountDto;
import ProjectDoge.StudentSoup.dto.member.MemberSearch;
import ProjectDoge.StudentSoup.entity.member.Member;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    Member fullFindById(Long id);
    Optional<Member> updateFindById(Long id);
    Optional<Member> findById(String id);
    Optional<Member> findByIdAndPwd(String id, String pwd);
    Optional<MemberFindAccountDto> findByAccountUsingEmail(String email);
    Optional<MemberFindAccountDto> findByAccountUsingEmailAndId(String email, String id);
    List<Member> findByName(String name);
    List<Member> findByNameAndSchool_SchoolName(String name, String schoolName);
    List<Member> findByDepartment_Id(Long id);
    List<Member> findBySchool_SchoolId(Long id);
    List<Member> search(String field,String value);
    Member findByNickname(String nickname);
    Member findByEmail(String email);
    Member findByEmailAndId(String email, String id);
}
