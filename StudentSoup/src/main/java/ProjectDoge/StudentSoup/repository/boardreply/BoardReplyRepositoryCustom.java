package ProjectDoge.StudentSoup.repository.boardreply;

import ProjectDoge.StudentSoup.dto.member.MemberMyPageBoardReplyDto;
import ProjectDoge.StudentSoup.entity.board.BoardReply;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardReplyRepositoryCustom {

    Page<MemberMyPageBoardReplyDto> findByMemberIdForMyPage(Long memberId, Pageable pageable);
    Long countByMemberId(Long memberId);
    List<BoardReply> findByBoardId(Long boardId);
    JPAQuery<Long> pagingCountByBoardId(Long boardId);
    List<BoardReply> findBestReplyByBoardId(Long boardId);
    List<BoardReply> findBySeq(Long boardId,int seq);
    Optional<Integer> findMaxSeqByBoardId(Long boardId);
    Optional<Integer> findMaxDepthByReplyId(Long boardId, int seq);
}
