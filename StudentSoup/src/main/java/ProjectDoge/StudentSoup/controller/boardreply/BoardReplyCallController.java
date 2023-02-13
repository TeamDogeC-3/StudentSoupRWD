package ProjectDoge.StudentSoup.controller.boardreply;

import ProjectDoge.StudentSoup.service.boardreply.BoardReplyCallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardReplyCallController {

    private final BoardReplyCallService boardReplyCallService;

    @GetMapping("/boardReplies/{boardId}/{memberId}")
    public ConcurrentHashMap<String,Object> callBoardReply(@PathVariable Long memberId, @PathVariable Long boardId){
        return boardReplyCallService.callBoardReply(memberId, boardId);
    }

}
