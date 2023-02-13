package ProjectDoge.StudentSoup.service.boardreply;


import ProjectDoge.StudentSoup.dto.boardreply.BoardReplyUpdateDto;
import ProjectDoge.StudentSoup.entity.board.BoardReply;
import ProjectDoge.StudentSoup.exception.boardreply.BoardReplyNotOwnException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardReplyEditService {

    private final BoardReplyFindService boardReplyFindService;
    private final BoardReplyValidationService boardReplyValidationService;

    public BoardReplyUpdateDto findEditBoardReply(Long boardReplyId, Long memberId){
        BoardReply boardReply = boardReplyFindService.findOne(boardReplyId);
        checkBoardReplyOwn(memberId, boardReply);
        BoardReplyUpdateDto boardReplyUpdateDto = new BoardReplyUpdateDto().createBoardReplyUpdateDto(boardReply);
        return boardReplyUpdateDto;
    }

    @Transactional
    public Long editBoardReply(BoardReplyUpdateDto boardReplyUpdateDto, Long boardReviewId){
        boardReplyValidationService.checkContent(boardReplyUpdateDto.getContent());
        BoardReply boardReply = boardReplyFindService.findOne(boardReviewId);
        boardReply.editBoardReview(boardReplyUpdateDto.getContent());
        return boardReply.getReplyId();
    }

    private void checkBoardReplyOwn(Long memberId, BoardReply boardReply) {
        if (!boardReply.getMember().getMemberId().equals(memberId)){
            throw new BoardReplyNotOwnException("해당 댓글은 회원이 작성한 리뷰가 아닙니다.");
        }
    }

}
