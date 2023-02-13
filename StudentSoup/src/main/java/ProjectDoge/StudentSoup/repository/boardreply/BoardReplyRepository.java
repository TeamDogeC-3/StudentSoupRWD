package ProjectDoge.StudentSoup.repository.boardreply;

import ProjectDoge.StudentSoup.entity.board.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>, BoardReplyRepositoryCustom {
}
