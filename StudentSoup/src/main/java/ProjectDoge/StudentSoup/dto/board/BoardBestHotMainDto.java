package ProjectDoge.StudentSoup.dto.board;

import ProjectDoge.StudentSoup.entity.board.BoardCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BoardBestHotMainDto extends BoardMainDto {
    private Long boardId;
    private String boardCategory;
    private String title;
    private String writeDate;
    private String nickname;
    private int view;
    private int likedCount;
    private int reviewCount;
    private String authentication;


    @QueryProjection
    public BoardBestHotMainDto(
            Long boardId,
            BoardCategory boardCategory,
            String title,
            String writeDate,
            String nickname,
            int view,
            int likedCount,
            int reviewCount,
            String authentication) {
        this.boardId = boardId;
        this.boardCategory = replaceCategory(boardCategory.getBoardCategory());
        this.title = title;
        this.writeDate = writeDate;
        this.nickname = nickname;
        this.view = view;
        this.likedCount = likedCount;
        this.authentication = authentication;
        this.reviewCount = reviewCount;
    }

    private String replaceCategory(String category){
        return category.replaceAll("게시판", "");
    }
}
