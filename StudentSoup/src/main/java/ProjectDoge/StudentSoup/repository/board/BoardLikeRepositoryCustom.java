package ProjectDoge.StudentSoup.repository.board;

import ProjectDoge.StudentSoup.entity.board.BoardLike;

import java.util.Optional;

public interface BoardLikeRepositoryCustom {

    Optional<BoardLike> findByBoardIdAndMemberId(Long boardId, Long MemberId);
}
