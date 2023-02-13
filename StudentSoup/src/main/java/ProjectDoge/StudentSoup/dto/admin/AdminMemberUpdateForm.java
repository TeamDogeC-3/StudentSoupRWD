package ProjectDoge.StudentSoup.dto.admin;

import ProjectDoge.StudentSoup.dto.member.MemberUpdateDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class AdminMemberUpdateForm extends MemberUpdateDto {
    private Long memberId;
    private Long schoolId;
    private String schoolName;
    private Long departmentId;
    private String departmentName;
    private String id;
    private String pwd;
    private String nickname;
    private String email;

    private MultipartFile multipartFile;

    //== 생성 메서드 ==//
    public AdminMemberUpdateForm createMemberUpdateForm(Member member){
        this.memberId = member.getMemberId();
        this.schoolId = member.getSchool().getId();
        this.schoolName = member.getSchool().getSchoolName();
        this.departmentId = member.getDepartment().getId();
        this.departmentName = member.getDepartment().getDepartmentName();
        this.id = member.getId();
        this.pwd = member.getPwd();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        return this;
    }
}
