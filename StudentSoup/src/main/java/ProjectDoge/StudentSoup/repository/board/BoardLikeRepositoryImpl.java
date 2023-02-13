package ProjectDoge.StudentSoup.repository.board;

import ProjectDoge.StudentSoup.entity.board.BoardLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.board.QBoard.board;
import static ProjectDoge.StudentSoup.entity.board.QBoardLike.boardLike;
import static ProjectDoge.StudentSoup.entity.member.QMember.member;

@RequiredArgsConstructor
public class BoardLikeRepositoryImpl implements BoardLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<BoardLike> findByBoardIdAndMemberId(Long boardId, Long memberId){
        BoardLike query = queryFactory.
                select(boardLike)
                .from(boardLike)
                .where(boardLike.board.id.eq(boardId),boardLike.member.memberId.eq(memberId))
                .fetchOne();
        return Optional.ofNullable(query);
    }
}
