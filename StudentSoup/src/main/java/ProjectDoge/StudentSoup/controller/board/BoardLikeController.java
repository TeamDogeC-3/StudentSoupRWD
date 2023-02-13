package ProjectDoge.StudentSoup.controller.board;


import ProjectDoge.StudentSoup.service.board.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/board/{boardId}/{memberId}/like")
    public ResponseEntity<ConcurrentHashMap<String,Object>> boardLike(@PathVariable Long boardId,@PathVariable Long memberId){
        ConcurrentHashMap<String, Object> resultMap = boardLikeService.boardLike(boardId, memberId);
        return ResponseEntity.ok(resultMap);
    }
}
