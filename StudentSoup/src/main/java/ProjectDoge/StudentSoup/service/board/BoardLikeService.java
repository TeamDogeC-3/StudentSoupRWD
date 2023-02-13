package ProjectDoge.StudentSoup.service.board;

import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.board.BoardDto;
import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.board.BoardLike;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.repository.board.BoardLikeRepository;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final MemberFindService memberFindService;
    private final BoardFindService boardFindService;
    

    @Transactional
    public ConcurrentHashMap<String,Object> boardLike(Long boardId, Long memberId){
        log.info("좋아요 / 좋아요 취소 서비스 로직이 실행되었습니다.");
        ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
        BoardLike boardLike = boardLikeRepository.findByBoardIdAndMemberId(boardId, memberId).orElse(null);
        Member member = memberFindService.findOne(memberId);
        Board board = boardFindService.findOne(boardId);
        if (boardLike==null){
            likeBoard(member, board, resultMap);
            return resultMap;
        }
        else{
            unLikeBoard(boardLike, board, resultMap);
            return resultMap;
        }
    }

    private void unLikeBoard(BoardLike boardLike, Board board, ConcurrentHashMap<String, Object> resultMap) {
        log.info("좋아요 취소 서비스 로직이 실행되었습니다.");
        boardLikeRepository.delete(boardLike);
        board.minusLikeCount();
        BoardDto dto = new BoardDto(board, ConstField.NOT_LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "cancel");
        log.info("게시글 좋아요가 취소 되었습니다.");
    }

    private void likeBoard(Member member, Board board, ConcurrentHashMap<String,Object> resultMap) {
        log.info("게시글 좋아요 서비스 로직이 실행되었습니다.");
        BoardLike boardLike = new BoardLike().createBoard(member, board);
        boardLikeRepository.save(boardLike);
        board.addLikeCount();
        BoardDto dto = new BoardDto(board, ConstField.LIKED);
        resultMap.put("data", dto);
        resultMap.put("result", "like");
        log.info("게시글 좋아요가 저장되었습니다.");
    }
}
