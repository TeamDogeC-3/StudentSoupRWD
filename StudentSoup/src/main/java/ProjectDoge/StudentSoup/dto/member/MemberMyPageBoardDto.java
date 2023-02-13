package ProjectDoge.StudentSoup.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class MemberMyPageBoardDto {

    private Long boardId;
    private String title;
    private String writeDate;
    private int likedCount;
    private int viewCount;


    public MemberMyPageBoardDto(){
    }

    @QueryProjection
    public MemberMyPageBoardDto(Long boardId, String title, LocalDateTime writeDate, int likedCount, int viewCount){
        this.boardId = boardId;
        this.title = title;
        this.writeDate = writeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;
        this.likedCount = likedCount;
        this.viewCount = viewCount;
    }
}
