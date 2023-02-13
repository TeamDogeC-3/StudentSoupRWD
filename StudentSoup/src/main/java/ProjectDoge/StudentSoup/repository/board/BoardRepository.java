package ProjectDoge.StudentSoup.repository.board;

import ProjectDoge.StudentSoup.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long>,BoardRepositoryCustom {
}
