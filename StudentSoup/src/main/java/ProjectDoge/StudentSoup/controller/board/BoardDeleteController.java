package ProjectDoge.StudentSoup.controller.board;

import ProjectDoge.StudentSoup.repository.board.BoardRepository;
import ProjectDoge.StudentSoup.service.board.BoardDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardDeleteController {

    private final BoardDeleteService boardDeleteService;
    @DeleteMapping("/board/{boardId}/{memberId}")
    public ConcurrentHashMap<String, Object> deleteBoard(@PathVariable Long boardId, @PathVariable Long memberId){
        ConcurrentHashMap<String, Object> resultMap = boardDeleteService.deleteBoard(boardId, memberId);
        return resultMap;
    }
}
