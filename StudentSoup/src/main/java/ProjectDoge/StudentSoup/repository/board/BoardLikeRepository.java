package ProjectDoge.StudentSoup.repository.board;

import ProjectDoge.StudentSoup.entity.board.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike,Long>,BoardLikeRepositoryCustom {

}
