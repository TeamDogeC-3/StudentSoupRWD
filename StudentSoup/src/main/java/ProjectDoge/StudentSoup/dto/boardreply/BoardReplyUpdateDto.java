package ProjectDoge.StudentSoup.dto.boardreply;


import ProjectDoge.StudentSoup.entity.board.BoardReply;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplyUpdateDto {

    private Long boardReplyId;
    private Long boardId;
    private Long memberId;
    private String content;


    public BoardReplyUpdateDto createBoardReplyUpdateDto(BoardReply boardReply) {
        this.boardReplyId = boardReply.getReplyId();
        this.boardId = boardReply.getBoard().getId();
        this.memberId = boardReply.getMember().getMemberId();
        this.content = boardReply.getContent();
        return this;
    }
}
