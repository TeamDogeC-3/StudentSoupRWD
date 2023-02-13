package ProjectDoge.StudentSoup.dto.member;

import ProjectDoge.StudentSoup.entity.member.GenderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@Setter
public class MemberFormBDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String pwd;
    private String nickname;
    private String email;
    private GenderType gender;
    private Long schoolId;
    private Long departmentId;
}
