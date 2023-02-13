package ProjectDoge.StudentSoup.dto.board;

import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.board.BoardCategory;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class BoardFormDto {

    private Long departmentId;
    private String title;
    private BoardCategory boardCategory;
    private String content;
    private String ip;
    private List<MultipartFile> multipartFileList;


    //== 생성 메서드 ==//
    private void setBoard(Board board){
        this.setDepartmentId(board.getDepartment().getId());
        this.setTitle(board.getTitle());
        this.setBoardCategory(board.getBoardCategory());
        this.setContent(board.getContent());

    }
    public BoardFormDto createBoardFormDto(String title, BoardCategory category, String content){
        this.title = title;
        this.boardCategory = category;
        this.content = content;
        return this;
    }

    public BoardFormDto createTestAllBoardFormDto(String title, BoardCategory category, String content){
        this.departmentId = null;
        this.title = title;
        this.boardCategory = category;
        this.content = content;
        return this;
    }
}
