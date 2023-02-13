package ProjectDoge.StudentSoup.dto.boardreply;

import ProjectDoge.StudentSoup.entity.board.BoardReply;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class BoardReplyDto {

    private Long boardReplyId;
    private String content;
    private int likeCount;
    private String nickname;
    private String writeDate;
    private String memberProfileImageName;
    private int seq;
    private int depth;
    private int level;
    private String active;
    private boolean like;

    public BoardReplyDto createBoardReplyDto(BoardReply boardReply, Boolean like) {
        memberNullCheck(boardReply);
        this.boardReplyId = boardReply.getReplyId();
        this.content = boardReply.getContent();
        this.likeCount = boardReply.getLikedCount();
        this.writeDate = boardReply.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;
        this.seq = boardReply.getSeq();
        this.depth = boardReply.getDepth();
        this.level = boardReply.getLevel();
        this.active = boardReply.getActive();
        this.like = like;
        return this;
    }

    private void memberNullCheck(BoardReply boardReply) {
        if(boardReply.getMember()==null){
            this.nickname = null;
            this.memberProfileImageName = null;
        }
        else {
            this.nickname = boardReply.getMember().getNickname();
            this.memberProfileImageName = setProfileImageFileName(boardReply.getMember());
        }
    }

    private String setProfileImageFileName(Member member){
        if(member.getImageFile() != null){
            return member.getImageFile().getFileName();
        }
        return null;
    }

}
