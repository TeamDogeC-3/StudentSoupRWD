package ProjectDoge.StudentSoup.repository.boardreply;


import ProjectDoge.StudentSoup.dto.member.MemberMyPageBoardReplyDto;
import ProjectDoge.StudentSoup.dto.member.QMemberMyPageBoardReplyDto;
import ProjectDoge.StudentSoup.entity.board.BoardReply;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.board.QBoard.board;
import static ProjectDoge.StudentSoup.entity.board.QBoardReply.boardReply;

@RequiredArgsConstructor
public class BoardReplyRepositoryImpl implements BoardReplyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberMyPageBoardReplyDto> findByMemberIdForMyPage(Long memberId, Pageable pageable) {

        List<MemberMyPageBoardReplyDto> content = queryFactory.select(new QMemberMyPageBoardReplyDto(
                        boardReply.board.id, boardReply.content, boardReply.writeDate, boardReply.likedCount))
                .from(boardReply)
                .where(boardReply.member.memberId.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(boardReply.writeDate.desc())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(boardReply.count())
                .from(boardReply)
                .where(boardReply.member.memberId.eq(memberId));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Long countByMemberId(Long memberId) {
        return queryFactory.select(boardReply.count())
                .from(boardReply)
                .where(boardReply.member.memberId.eq(memberId))
                .fetchOne();
    }

    @Override
    public List<BoardReply> findByBoardId(Long boardId) {
        List<BoardReply> query = queryFactory
                .select(boardReply)
                .from(boardReply)
                .leftJoin(boardReply.board, board)
                .fetchJoin()
                .where(boardReply.board.id.eq(boardId))
                .orderBy(boardReply.seq.asc(), boardReply.depth.asc())
                .fetch();

        return query;
    }

    @Override
    public JPAQuery<Long> pagingCountByBoardId(Long boardId) {
        return queryFactory
                .select(boardReply.count())
                .from(boardReply)
                .where(boardReply.board.id.eq(boardId));
    }

    @Override
    public List<BoardReply> findBestReplyByBoardId(Long boardId) {
        List<BoardReply> query = queryFactory
                .select(boardReply)
                .from(boardReply)
                .leftJoin(boardReply.board, board)
                .fetchJoin()
                .where(boardReply.board.id.eq(boardId), boardReply.likedCount.goe(10))
                .orderBy(boardReply.likedCount.desc())
                .offset(0)
                .limit(3)
                .fetch();

        return query;
    }

    @Override
    public List<BoardReply> findBySeq(Long boardId,int seq) {
        List<BoardReply> query = queryFactory
                .select(boardReply)
                .from(boardReply)
                .where(boardReply.board.id.eq(boardId),boardReply.seq.eq(seq))
                .orderBy(boardReply.depth.asc())
                .fetch();
        return query;
    }

    @Override
    public Optional<Integer> findMaxSeqByBoardId(Long boardId) {
        Integer query = queryFactory.select(boardReply.seq.max())
                .from(boardReply)
                .where(boardReply.board.id.eq(boardId))
                .fetchOne();

        return Optional.ofNullable(query);
    }

    @Override
    public Optional<Integer> findMaxDepthByReplyId(Long boardId, int seq) {
        Integer query =  queryFactory.select(boardReply.depth.max())
                .from(boardReply)
                .where(boardReply.board.id.eq(boardId), boardReply.seq.eq(seq))
                .fetchOne();

        return Optional.ofNullable(query);
    }
}
