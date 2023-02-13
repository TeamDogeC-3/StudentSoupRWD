package ProjectDoge.StudentSoup.dto.boardreply;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplyReqDto {

    private Long boardId;
    private Long memberId;
    private String content;
    private Integer seq;
    private Integer depth;
    private int level;

    public BoardReplyReqDto(Long boardId, Long memberId, String content, Integer seq, Integer depth, int level) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.content = content;
        this.seq = seq;
        this.depth = depth;
        this.level = level;
    }

    protected BoardReplyReqDto(){

    }

}
