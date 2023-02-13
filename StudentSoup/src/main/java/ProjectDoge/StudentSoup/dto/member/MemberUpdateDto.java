package ProjectDoge.StudentSoup.dto.member;

import lombok.Data;

@Data
public class MemberUpdateDto {
    private Long memberId;
    private Long schoolId;
    private String schoolName;
    private Long departmentId;
    private String departmentName;
    private String id;
    private String pwd;
    private String nickname;
    private String email;

    //== 생성 메서드 ==//
    public MemberUpdateDto(){}
}
