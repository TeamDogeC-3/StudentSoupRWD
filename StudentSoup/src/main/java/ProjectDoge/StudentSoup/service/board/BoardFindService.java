package ProjectDoge.StudentSoup.service.board;

import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.exception.board.BoardIdNotSentException;
import ProjectDoge.StudentSoup.exception.board.BoardNotFoundException;
import ProjectDoge.StudentSoup.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardFindService {
    private final BoardRepository boardRepository;

    public Board findOne(Long boardId){
        checkBoardIdSent(boardId);

        return boardRepository.findById(boardId).orElseThrow(()->{
            log.info("findOne 메소드가 실행되었습니다.[{}]",boardId);
            throw new BoardNotFoundException("게시글을 조회하지 못했습니다.");
        });
    }

    public Board findOneForBoardDetail(Long boardId){
        checkBoardIdSent(boardId);

        return boardRepository.findByIdForBoardDetail(boardId).orElseThrow(()->{
            log.info("게시글 탐색 메소드가 실행되었습니다.[{}]", boardId);
            throw new BoardNotFoundException("게시글을 조회하지 못했습니다.");
        });
    }

    private void checkBoardIdSent(Long boardId) {
        if(boardId == null){
            log.info("boardId가 전송되지 않았습니다.");
            throw new BoardIdNotSentException("boardId가 전송되지 않았습니다.");
        }
    }

}
