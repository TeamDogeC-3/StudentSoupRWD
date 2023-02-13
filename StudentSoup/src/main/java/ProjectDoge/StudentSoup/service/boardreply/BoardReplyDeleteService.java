package ProjectDoge.StudentSoup.service.boardreply;


import ProjectDoge.StudentSoup.entity.board.BoardReply;
import ProjectDoge.StudentSoup.exception.boardreply.BoardReplyNotOwnException;
import ProjectDoge.StudentSoup.repository.boardreply.BoardReplyLikeRepository;
import ProjectDoge.StudentSoup.repository.boardreply.BoardReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardReplyDeleteService {

    private final BoardReplyFindService boardReplyFindService;

    private final BoardReplyRepository boardReplyRepository;

    private final BoardReplyLikeRepository boardReplyLikeRepository;

    @Transactional
    public ConcurrentHashMap<String,Object> deleteBoardReply(Long boardReplyId, Long memberId){
        ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<>();
        BoardReply boardReply = boardReplyFindService.findOne(boardReplyId);
        checkBoardReplyOwn(memberId, boardReply);
        checkReplyLevel(boardReply);
        resultMap.put("result", "ok");
        resultMap.put("boardReplyId", boardReplyId);
        return resultMap;
    }

    private void checkReplyLevel(BoardReply boardReply) {
        if(boardReply.getLevel() == 0){
            log.info("boardReplyLevel [{}]",boardReply.getLevel());
            List<BoardReply> boardReplyList = boardReplyRepository.findBySeq(boardReply.getBoard().getId(),boardReply.getSeq());
            deleteReply(boardReply, boardReplyList);

        }
        else{
            List<BoardReply> boardReplyList = boardReplyRepository.findBySeq(boardReply.getBoard().getId(),boardReply.getSeq());
            deleteNestedReply(boardReply, boardReplyList);
        }
    }

    private void deleteReply(BoardReply boardReply, List<BoardReply> boardReplyList) {
        if (boardReplyList.size() == 1) {
            boardReplyRepository.delete(boardReply);
        }
        else{
            boardReply.setActive("N");
            boardReply.setContent("삭제된 댓글 입니다.");
            boardReply.deleteMember();
            boardReplyLikeRepository.deleteAllInBatch(boardReply.getBoardReplyLikes());
        }
    }

    private void deleteNestedReply(BoardReply boardReply, List<BoardReply> boardReplyList) {
        log.info("boardReply.size : [{}]",boardReplyList.size());
        log.info("boardReply.(0).id [{}]",boardReplyList.get(0).getReplyId());
        if(boardReplyList.size() <= 2 && boardReplyList.get(0).getActive().equals("N")){
            boardReplyRepository.delete(boardReply);
            boardReplyRepository.delete(boardReplyList.get(0));
        }
        else{
            boardReplyRepository.delete(boardReply);
        }
    }

    private void checkBoardReplyOwn(Long memberId, BoardReply boardReply) {
        if (!boardReply.getMember().getMemberId().equals(memberId)){
            throw new BoardReplyNotOwnException("해당 댓글은 해당 회원이 작성한 리뷰가 아닙니다.");
        }
    }
}
