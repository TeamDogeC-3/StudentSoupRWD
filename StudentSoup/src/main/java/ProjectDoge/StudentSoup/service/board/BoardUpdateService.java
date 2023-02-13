package ProjectDoge.StudentSoup.service.board;
import ProjectDoge.StudentSoup.commonmodule.ConstField;
import ProjectDoge.StudentSoup.dto.board.BoardDto;
import ProjectDoge.StudentSoup.dto.board.BoardFormDto;
import ProjectDoge.StudentSoup.dto.board.BoardUpdateDto;
import ProjectDoge.StudentSoup.dto.file.UploadFileDto;
import ProjectDoge.StudentSoup.entity.board.Board;
import ProjectDoge.StudentSoup.entity.board.BoardLike;
import ProjectDoge.StudentSoup.entity.file.ImageFile;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.entity.school.Department;
import ProjectDoge.StudentSoup.exception.board.BoardNotOwnMemberException;
import ProjectDoge.StudentSoup.repository.board.BoardLikeRepository;
import ProjectDoge.StudentSoup.repository.board.BoardRepository;
import ProjectDoge.StudentSoup.repository.department.DepartmentRepository;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardUpdateService {

    private final BoardFindService boardFindService;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final DepartmentRepository departmentRepository;
    private final MemberFindService memberFindService;
    private final BoardValidationService boardValidationService;


    public BoardUpdateDto findEditBoard(Long boardId, Long memberId){
        Board board = boardFindService.findOne(boardId);
        checkOwnMember(memberId, board);
        BoardUpdateDto boardFormDto = new BoardUpdateDto().createBoardFormDto(board);
        return boardFormDto;
    }

    @Transactional
    public BoardDto editBoard(BoardFormDto boardFormDto, Long boardId, Long memberId, List<MultipartFile> multipartFiles){
        log.info("게시판 업데이트 서비스가 실행되었습니다.");
        Board board = boardFindService.findOne(boardId);
        Member member = memberFindService.findOne(memberId);
        boardValidationService.checkValidation(boardFormDto, member);
        updateBoardImage(multipartFiles, board);
        Department department = departmentRepository.findById(boardFormDto.getDepartmentId()).orElse(null);
        board.editBoard(boardFormDto, department);
        boardRepository.save(board);
        return getBoardDto(boardId, memberId, board);
    }

    private void checkOwnMember(Long memberId, Board board) {
        if(!board.getMember().getMemberId().equals(memberId)){
            throw new BoardNotOwnMemberException("해당 게시글의 작성자가 아닙니다.");
        }
    }

    private void updateBoardImage(List<MultipartFile> multipartFiles, Board board) {
        log.info("게시글 이미지 업데이트 호출 메소드가 실행되었습니다.");
        if(multipartFiles != null && !multipartFiles.isEmpty()){
            log.info("게시글 이미지가 존재하며 기존 이미지를 삭제하고 새로 업데이트 합니다.");
            deleteImageFile(board);
            uploadBoardImage(board, multipartFiles);
        }
        log.info("게시글 이미지 업데이트가 완료되었습니다.");
    }

    private void deleteImageFile(Board board) {
        fileRepository.deleteAllInBatch(board.getImageFileList());
    }

    private void uploadBoardImage(Board board, List<MultipartFile> multipartFiles) {
        List<UploadFileDto> uploadFileDtoList = fileService.createUploadFileDtoList(multipartFiles);
        for(UploadFileDto fileDto : uploadFileDtoList){
            ImageFile imageFile = new ImageFile().createFile(fileDto);
            fileRepository.save(imageFile);
            board.addImageFile(imageFile);
        }
    }

    private BoardDto getBoardDto(Long boardId, Long memberId, Board board) {
        BoardLike boardLike = boardLikeRepository.findByBoardIdAndMemberId(boardId, memberId).orElse(null);
        if(boardLike == null) {
            return new BoardDto(board, ConstField.NOT_LIKED);
        }
        return new BoardDto(board, ConstField.LIKED);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBoardView(Board board){
        board.setIsView("Y");
        boardRepository.save(board);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBoardAuthentication(Board board){
        board.setAuthentication("Y");
        boardRepository.save(board);
    }
}
