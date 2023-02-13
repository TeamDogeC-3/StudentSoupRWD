package ProjectDoge.StudentSoup.controller.boardreply;

import ProjectDoge.StudentSoup.service.boardreply.BoardReplyLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class BoardReplyLikeController {

    private final BoardReplyLikeService boardReplyLikeService;

    @PostMapping("/boardReply/{boardReplyId}/{memberId}/like")
    public ConcurrentHashMap<String,Object> BoardReplyLike(@PathVariable Long memberId, @PathVariable Long boardReplyId){
        return boardReplyLikeService.boardReplyLike(memberId, boardReplyId);
    }
}
