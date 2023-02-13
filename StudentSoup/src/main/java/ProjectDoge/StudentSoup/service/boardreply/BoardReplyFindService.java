package ProjectDoge.StudentSoup.service.boardreply;


import ProjectDoge.StudentSoup.entity.board.BoardReply;
import ProjectDoge.StudentSoup.exception.boardreply.BoardReplyIdNotSentException;
import ProjectDoge.StudentSoup.exception.boardreply.BoardReplyNotFoundException;
import ProjectDoge.StudentSoup.repository.boardreply.BoardReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardReplyFindService {
    private final BoardReplyRepository boardReplyRepository;

    public BoardReply findOne(Long boardReplyId){
        checkBoardReplyIdSent(boardReplyId);
        return boardReplyRepository.findById(boardReplyId).orElseThrow(() -> {
            throw new BoardReplyNotFoundException("등록되지 않은 댓글 입니다.");
        });
    }

    private void checkBoardReplyIdSent(Long boardReplyId) {
        if (boardReplyId == null){
            log.info("boardReplyId 가 전송되지 않았습니다.");
            throw new BoardReplyIdNotSentException("boardReplyId 가 전송되지 않았습니다.");
        }
    }

}
