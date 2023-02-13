package ProjectDoge.StudentSoup.controller.boardreply;

import ProjectDoge.StudentSoup.service.boardreply.BoardReplyDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardReplyDeleteController {
    private  final BoardReplyDeleteService boardReplyDeleteService;

    @DeleteMapping("/boardReply/{boardReplyId}/{memberId}")
    public ConcurrentHashMap<String,Object> deleteBoardReply(@PathVariable Long boardReplyId, @PathVariable Long memberId){
        return boardReplyDeleteService.deleteBoardReply(boardReplyId, memberId);
    }
}
