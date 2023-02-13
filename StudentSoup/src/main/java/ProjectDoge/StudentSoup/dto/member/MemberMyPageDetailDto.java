package ProjectDoge.StudentSoup.dto.member;

import lombok.Data;

@Data
public class MemberMyPageDetailDto {

    private long boardWriteCount;
    private long boardReplyWriteCount;

    public MemberMyPageDetailDto(long boardWriteCount, long boardReplyWriteCount){
        this.boardWriteCount = boardWriteCount;
        this.boardReplyWriteCount = boardReplyWriteCount;
    }
}
