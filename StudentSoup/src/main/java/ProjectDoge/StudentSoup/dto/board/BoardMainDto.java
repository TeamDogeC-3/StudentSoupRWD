package ProjectDoge.StudentSoup.dto.board;

import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.board.BoardCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class BoardMainDto {
    private Long boardId;
    private String boardCategory;
    private String tag;
    private String title;
    private String writeDate;
    private String nickname;
    private int view;
    private int likedCount;
    private int reviewCount;
    private String authentication;

    public BoardMainDto(Board board) {
        this.boardId = board.getId();
        this.boardCategory = board.getBoardCategory().getBoardCategory();
        this.title = board.getTitle();
        this.writeDate = board.getWriteDate().toLocalDate().toString();
        this.likedCount = board.getLikedCount();
        this.view = board.getView();
        this.nickname = board.getMember().getNickname();
        this.authentication = board.getAuthentication();
    }

    @QueryProjection
    public BoardMainDto(
            Long boardId,
            BoardCategory boardCategory,
            String title,
            LocalDateTime writeDate,
            String nickname,
            int view,
            int likedCount,
            int reviewCount,
            String authentication) {

        this.boardId = boardId;
        this.boardCategory = boardCategory.getBoardCategory();
        this.tag = replaceCategory(boardCategory.getBoardCategory());
        this.title = title;
        this.writeDate = writeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;
        this.nickname = nickname;
        this.view = view;
        this.likedCount = likedCount;
        this.authentication = authentication;
        this.reviewCount = reviewCount;
    }

    private String replaceCategory(String category){
        category = category.replaceAll("게시판", "");
        return category.replaceAll("사항", "");
    }

    protected BoardMainDto() {

    }
}
