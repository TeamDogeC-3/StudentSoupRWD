package ProjectDoge.StudentSoup.service.boardreply;


import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.boardreply.BoardReplyDto;
import ProjectDoge.StudentSoup.entity.board.BoardLike;
import ProjectDoge.StudentSoup.entity.board.BoardReply;
import ProjectDoge.StudentSoup.entity.board.BoardReplyLike;
import ProjectDoge.StudentSoup.repository.boardreply.BoardReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardReplyCallService {
    private final BoardReplyRepository boardReplyRepository;

    public ConcurrentHashMap<String, Object> callBoardReply(Long memberId, Long boardId) {
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

        List<BoardReply> boardReplyList = boardReplyRepository.findByBoardId(boardId);
        List<BoardReplyDto> boardReplyDtoList = checkBoardReplyLike(memberId, boardReplyList);

        List<BoardReply> bestReview = boardReplyRepository.findBestReplyByBoardId(boardId);
        List<BoardReplyDto> bestReviewDtoList = checkBoardReplyLike(memberId, bestReview);

        resultMap.put("boardReplyList", boardReplyDtoList);
        resultMap.put("bestReplyList", bestReviewDtoList);

        return resultMap;
    }

    private List<BoardReplyDto> checkBoardReplyLike(Long memberId,
                                                    List<BoardReply> boardReplyList) {
        List<BoardReplyDto> boardReplyDtoList = new ArrayList<>();
        for (BoardReply boardReply : boardReplyList) {
            boardReplyDtoList.add(getBoardReplyLike(memberId, boardReply));
        }
        return boardReplyDtoList;
    }

    private BoardReplyDto getBoardReplyLike(Long memberId, BoardReply boardReply) {
        for (BoardReplyLike boardReplyLike : boardReply.getBoardReplyLikes()) {
            if (boardReplyLike.getMember().getMemberId().equals(memberId))
                return new BoardReplyDto().createBoardReplyDto(boardReply, ConstField.LIKED);
        }
        return new BoardReplyDto().createBoardReplyDto(boardReply, ConstField.NOT_LIKED);
    }
}
