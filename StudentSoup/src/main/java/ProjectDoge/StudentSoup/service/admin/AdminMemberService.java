package ProjectDoge.StudentSoup.service.admin;

import ProjectDoge.StudentSoup.dto.admin.AdminMemberUpdateForm;
import ProjectDoge.StudentSoup.dto.member.MemberUpdateDto;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.entity.school.School;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import ProjectDoge.StudentSoup.service.file.FileFindService;
import ProjectDoge.StudentSoup.service.file.FileService;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import ProjectDoge.StudentSoup.service.member.MemberValidationService;
import ProjectDoge.StudentSoup.service.school.SchoolFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final MemberValidationService memberValidationService;
    private final SchoolFindService schoolFindService;
    private final DepartmentFindService departmentFindService;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final FileFindService fileFindService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long adminMemberUpdate(AdminMemberUpdateForm dto, MultipartFile file) {
        log.info("운영자 페이지 회원 업데이트 메서드가 실행되었습니다.");

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> {
                    throw new MemberNotFoundException();
                });

        School school = schoolFindService.findOne(dto.getSchoolId());
        Department department = departmentFindService.findOne(dto.getDepartmentId());

        log.info("현재 가져온 학교와 학과 : [{}] / [{}]", school.getSchoolName(), department.getDepartmentName());
        validationChangedNicknameEmail(dto, member);
        adminUpdateMemberField(dto, member);
        log.info("회원 정보가 새로 갱신되었습니다.");
        Long fileId = fileService.join(file);
        updateMemberProfileImage(member, fileId);
        memberRepository.save(member);

        return member.getMemberId();
    }
    public void validationChangedNicknameEmail(MemberUpdateDto dto, Member member) {
        log.info("회원 업데이트 중 닉네임과 이메일 검증을 시작합니다.");
        if(!member.getNickname().equals(dto.getNickname())) {
            memberValidationService.validateDuplicateMemberNickname(dto.getNickname());
        }
        if(!member.getEmail().equals(dto.getEmail())) {
            memberValidationService.validateDuplicateMemberEmail(dto.getEmail());
        }
        log.info("회원 업데이트 중 닉네임 이메일 검증이 완료되었습니다.");
    }
    public List<Member> searchMember(String filed,String value){
        if(filed == null || filed.length() == 0 || value == null || value.length()==0){
            return  Collections.emptyList();
        }
        List<Member> findMembers = memberRepository.search(filed, value);
        return findMembers;
    }


    private void adminUpdateMemberField(MemberUpdateDto dto, Member member) {
        member.setPwd(passwordEncoder.encode(dto.getPwd()));
        member.setEmail(dto.getEmail());
        member.setNickname(dto.getNickname());
    }

    private void updateMemberProfileImage(Member member, Long fileId) {
        if(member.getImageFile() != null){
            fileService.deleteFile(member.getImageFile());
            fileRepository.delete(member.getImageFile());
        }

        if(fileId != null) {
            ImageFile imageFile = fileFindService.findOne(fileId);
            member.setImageFile(imageFile);
        }
    }
}
