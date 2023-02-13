package ProjectDoge.StudentSoup.service.boardreply;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.boardreply.BoardReplyDto;
import ProjectDoge.StudentSoup.entity.board.BoardReply;
import ProjectDoge.StudentSoup.entity.board.BoardReplyLike;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.repository.boardreply.BoardReplyLikeRepository;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardReplyLikeService {
    private final BoardReplyLikeRepository boardReplyLikeRepository;
    private final BoardReplyFindService boardReplyFindService;
    private final MemberFindService memberFindService;

    @Transactional
    public ConcurrentHashMap<String, Object> boardReplyLike(Long memberId, Long boardReplyId) {
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
        BoardReplyLike boardReplyLike = boardReplyLikeRepository.findBoardReplyLikeByReplyIdAndMemberId(memberId, boardReplyId).orElse(null);
        BoardReply boardReply = boardReplyFindService.findOne(boardReplyId);
        Member member = memberFindService.findOne(memberId);

        if (boardReplyLike == null) {
            likeBoardReply(boardReply, member, resultMap);
        } else {
            unlikeBoardReply(boardReply, boardReplyLike, resultMap);
        }
        return resultMap;
    }

    private void unlikeBoardReply(BoardReply boardReply, BoardReplyLike boardReplyLike, ConcurrentHashMap<String, Object> resultMap) {
        boardReplyLikeRepository.delete(boardReplyLike);
        boardReply.minusLikeCount();
        BoardReplyDto boardReplyDto = new BoardReplyDto().createBoardReplyDto(boardReply, ConstField.NOT_LIKED);
        resultMap.put("data", boardReplyDto);
        resultMap.put("result", "cancel");
    }

    private void likeBoardReply(BoardReply boardReply, Member member, ConcurrentHashMap<String, Object> resultMap) {
        BoardReplyLike boardReplyLike = new BoardReplyLike().createBoardReviewLike(boardReply, member);
        boardReplyLikeRepository.save(boardReplyLike);
        boardReply.addLikeCount();
        BoardReplyDto boardReplyDto = new BoardReplyDto().createBoardReplyDto(boardReply, ConstField.LIKED);
        resultMap.put("data", boardReplyDto);
        resultMap.put("result", "like");
    }
}
