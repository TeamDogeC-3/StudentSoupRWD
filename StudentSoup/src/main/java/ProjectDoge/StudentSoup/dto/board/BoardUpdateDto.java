package ProjectDoge.StudentSoup.dto.board;

import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.board.BoardCategory;
import ProjectDoge.StudentSoup.entity.school.Department;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BoardUpdateDto {

        private Long departmentId;
        private String title;
        private BoardCategory boardCategory;
        private String content;


        //== 생성 메서드 ==//
        private void setBoard(Board board) {
            this.setTitle(board.getTitle());
            this.setBoardCategory(board.getBoardCategory());
            this.setContent(board.getContent());
        }

        public BoardUpdateDto createBoardFormDto(Board board) {
            this.departmentId = setDepartment(board.getDepartment());
            this.title = board.getTitle();
            this.boardCategory = board.getBoardCategory();
            this.content = board.getContent();
            return this;
        }

        private Long setDepartment(Department department){
            if(department != null){
                return department.getId();
            }
            return 0L;
        }
    }