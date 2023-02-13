package ProjectDoge.StudentSoup.entity.board;

import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantLike;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOARD_LIKE")
@Getter
@Setter
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_LIKED_ID")
    private Member member;

    //== 연관관계 메서드==//
    public void setMember(Member member){
        if(this.member != null){
            this.member.getBoardLikes().remove(this);
        }
        this.member = member;
        member.getBoardLikes().add(this);
    }

    public void setBoard(Board board){
        if(this.board != null){
            this.board.getBoardLikes().remove(this);
        }
        this.board = board;
        board.getBoardLikes().add(this);
    }
    public BoardLike createBoard(Member member, Board board){
        this.board = board;
        this.member = member;

        return this;
    }

}
