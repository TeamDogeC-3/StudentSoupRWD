package ProjectDoge.StudentSoup.service.board;

import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.exception.board.BoardNotOwnMemberException;
import ProjectDoge.StudentSoup.repository.board.BoardRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class BoardDeleteService {

    private final BoardFindService boardFindService;
    private final BoardRepository boardRepository;
    private final FileService fileService;

    @Transactional
    public ConcurrentHashMap<String,Object> deleteBoard(Long boardId, Long memberId){
        ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<>();
        Board board = boardFindService.findOne(boardId);
        checkBoardOwn(board,memberId);
        ifPresentImageDelete(board);
        boardRepository.delete(board);
        resultMap.put("result","ok");
        resultMap.put("boardId",boardId);

        return resultMap;
    }

    private void ifPresentImageDelete(Board board) {
        for(ImageFile image : board.getImageFileList()){
            fileService.deleteFile(image);
        }
    }

    private void checkBoardOwn(Board board, Long memberId) {
        if (!board.getMember().getMemberId().equals(memberId)){
           throw new BoardNotOwnMemberException("해당 게시글의 작성자가 아닙니다.");
        }
    }

}
