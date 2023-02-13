package ProjectDoge.StudentSoup.entity.board;

import ProjectDoge.StudentSoup.dto.boardreply.BoardReplyReqDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "BOARD_REPLY")
@Getter
@Setter
public class BoardReply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime writeDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updateDate;

    @Size(min = 2, max = 500)
    private String content;

    private int likedCount;

    // 댓글 달린 순서(댓글 인덱스) default 1
    private int seq;

    // 댓글의 대댓글 깊이 default 0
    private int depth;

    // 댓글, 대댓글 여부 확인 0 == 댓글, 1 == 대댓글
    private int level;

    //댓글 삭제 여부
    private String active;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "boardReply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardReplyLike> boardReplyLikes;


    //== 연관 관계 메서드 ==//
    public void setMember(Member member){
        if(this.member != null){
            this.member.getBoardReplies().remove(this);
        }
        this.member = member;
        member.getBoardReplies().add(this);
    }

    public void deleteMember(){
        this.member.getBoardReplies().remove(this);
        this.member = null;
    }


    //== 생성 메서드 ==//
    public BoardReply createBoardReply(Member member, Board board, BoardReplyReqDto dto){
        this.setBoard(board);
        this.setMember(member);
        this.setContent(dto.getContent());
        this.setWriteDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.setLikedCount(0);
        this.setSeq(dto.getSeq());
        this.setDepth(dto.getDepth());
        this.setLevel(dto.getLevel());
        this.setActive("Y");
        return this;
    }

    public BoardReply createTestBoardReply(Member member, Board board, BoardReplyReqDto dto){
        this.setBoard(board);
        this.setMember(member);
        this.setContent(dto.getContent());
        this.setWriteDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.setLikedCount(10);
        this.setSeq(dto.getSeq());
        this.setDepth(dto.getDepth());
        this.setLevel(dto.getLevel());
        this.setActive("Y");
        return this;
    }

    public BoardReply createBoardNestedReply() {
        this.setWriteDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
        this.setLikedCount(0);
        this.setLevel(1);
        this.setActive("Y");
        return this;
    }
    private String dateFormat(LocalDateTime time){
        String formatTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return formatTime;
    }
    public BoardReply editBoardReview(String content){
        this.setContent(content);
        this.setUpdateDate(LocalDateTime.now());
        return this;
    }


    public void addLikeCount() {
        this.likedCount += 1;
    }

    public void minusLikeCount() {
        this.likedCount -= 1;
    }
}
