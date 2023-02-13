package ProjectDoge.StudentSoup.entity.board;

import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOARD_REPLY_LIKE")
@Getter
@Setter
public class BoardReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_REPLY_ID")
    private BoardReply boardReply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_REPLY_LIKED_ID")
    private Member member;

    public BoardReplyLike createBoardReviewLike(BoardReply boardReply, Member member) {
        this.boardReply = boardReply;
        this.member = member;
        return this;
    }

    //    == 연관 관계 메서드 ==//
    public void setMember(Member member){
        if(this.member != null){
            this.member.getBoardReplyLikes().remove(this);
        }
        this.member = member;
        member.getBoardReplyLikes().add(this);
    }
    public void setBoardReply(BoardReply boardReply){
        if(this.boardReply != null){
            this.boardReply.getBoardReplyLikes().remove(this);
        }
        this.boardReply = boardReply;
        boardReply.getBoardReplyLikes().add(this);
    }


}
