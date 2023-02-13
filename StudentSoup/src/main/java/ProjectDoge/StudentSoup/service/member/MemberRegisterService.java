package ProjectDoge.StudentSoup.service.member;

import ProjectDoge.StudentSoup.dto.member.MemberFormBDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.exception.member.MemberNicknameOutOfRangeException;
import ProjectDoge.StudentSoup.exception.member.MemberRegexException;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegisterService {

    private final MemberRepository memberRepository;
    private final MemberValidationService memberValidationService;
    private final SchoolFindService schoolFindService;
    private final DepartmentFindService departmentFindService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberFormBDto dto) {
        log.info("회원 생성 메서드가 실행되었습니다.");

        School school = schoolFindService.findOne(dto.getSchoolId());
        Department department = departmentFindService.findOne(dto.getDepartmentId());

        log.info("회원의 학교는 : {}, 회원의 학과는 : {}", school.getSchoolName(), department.getDepartmentName());

        validateMember(dto);

        Member member = new Member().createMember(dto, school, department);
        member.setPwd(passwordEncoder.encode(member.getPwd()));
        memberRepository.save(member);

        log.info("회원이 생성되었습니다. [{}][{}] ", member.getId(), member.getNickname());

        return member.getMemberId();
    }

    private void validateMember(MemberFormBDto dto) {
        validateDuplicateMember(dto);
        validateNickname(dto.getNickname());
    }

    private void validateDuplicateMember(MemberFormBDto dto) {
        memberValidationService.validateDuplicateMemberNickname(dto.getNickname());
        memberValidationService.validateDuplicateMemberEmail(dto.getEmail());
    }

    private void validateNickname(String nickname){
        if(nickname.length() < 2 || nickname.length() > 12){
            log.info("회원의 닉네임이 2자 미만이거나 12자를 초과하였습니다. 전달받은 nickname : [{}]", nickname);
            throw new MemberNicknameOutOfRangeException("회원의 닉네임이 2자 미만이거나 12자를 초과하였습니다.");
        }

        if(!nickname.matches("^[a-zA-Z0-9가-힣]*$")){
            log.info("회원의 닉네임에 특수문자가 포함되어 있습니다. 전달받은 nickname : [{}]", nickname);
            throw new MemberRegexException("회원의 닉네임에 특수문자가 포함되어 있습니다.");
        }
    }


}
