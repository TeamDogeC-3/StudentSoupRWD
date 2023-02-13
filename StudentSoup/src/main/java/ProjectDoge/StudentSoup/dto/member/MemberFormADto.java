package ProjectDoge.StudentSoup.dto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormADto {

    @NotEmpty
    private String id;
    @NotEmpty
    private String pwd;
}
