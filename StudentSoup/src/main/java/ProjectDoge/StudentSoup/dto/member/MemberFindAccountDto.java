package ProjectDoge.StudentSoup.dto.member;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberFindAccountDto {
    private String id;
    private String email;
    private String nickname;
    private String pwd;

    public MemberFindAccountDto(String id, String email, String nickname){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}
