package ProjectDoge.StudentSoup.repository.boardreply;

import ProjectDoge.StudentSoup.entity.board.BoardReplyLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.board.QBoardReplyLike.*;


@RequiredArgsConstructor
public class BoardReplyLikeRepositoryImpl implements BoardReplyLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<BoardReplyLike> findBoardReplyLikeByReplyIdAndMemberId(Long memberId, Long boardReplyId){
        BoardReplyLike query = queryFactory
                .select(boardReplyLike)
                .from(boardReplyLike)
                .where(boardReplyLike.member.memberId.eq(memberId),boardReplyLike.boardReply.replyId.eq(boardReplyId))
                .fetchOne();

        return Optional.ofNullable(query);
    }

}
