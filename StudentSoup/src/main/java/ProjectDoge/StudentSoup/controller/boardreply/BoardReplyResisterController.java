package ProjectDoge.StudentSoup.controller.boardreply;

import ProjectDoge.StudentSoup.dto.boardreply.BoardReplyReqDto;
import ProjectDoge.StudentSoup.service.boardreply.BoardReplyRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardReplyResisterController {

    private final BoardReplyRegisterService boardReplyRegisterService;

    @PutMapping("/boardReply")
    public ResponseEntity<ConcurrentHashMap<String, Object>> registerBoardReview(@RequestBody BoardReplyReqDto boardReplyReqDto){
        ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<>();
        Long boardReviewId = boardReplyRegisterService.join(boardReplyReqDto);
        resultMap.put("result","ok");
        resultMap.put("boardReviewId",boardReviewId);
        return ResponseEntity.ok(resultMap);

    }
}
