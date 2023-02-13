package ProjectDoge.StudentSoup.dto.member;

import lombok.Data;

@Data
public class MemberUpdateValidationDto {
    private Long memberId;
    private String id;
    private String pwd;
}
