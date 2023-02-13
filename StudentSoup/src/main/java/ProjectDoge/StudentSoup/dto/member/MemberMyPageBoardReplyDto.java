package ProjectDoge.StudentSoup.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class MemberMyPageBoardReplyDto {

    private Long boardId;
    private String content;
    private String writeDate;
    private int likedCount;

    public MemberMyPageBoardReplyDto(){
    }

    @QueryProjection
    public MemberMyPageBoardReplyDto(Long boardId, String content, LocalDateTime writeDate, int likedCount){
        this.boardId = boardId;
        this.content = content;
        this.writeDate = writeDate.toString();
        this.likedCount = likedCount;
    }
}
