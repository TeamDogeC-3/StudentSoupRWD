package ProjectDoge.StudentSoup.controller.board;

import ProjectDoge.StudentSoup.dto.board.BoardCallDto;
import ProjectDoge.StudentSoup.dto.board.BoardDto;
import ProjectDoge.StudentSoup.dto.board.BoardSearchDto;
import ProjectDoge.StudentSoup.dto.department.DepartmentCallDto;
import ProjectDoge.StudentSoup.exception.page.PagingLimitEqualsZeroException;
import ProjectDoge.StudentSoup.service.board.BoardCallService;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardCallController {

    private final BoardCallService boardCallService;
    private final DepartmentFindService departmentFindService;

    /**
     * @param category
     * @param sorted  0 normal(작성 순), 1(좋아요 5개 이상), 2(좋아요 순)
     * @param boardCallDto schoolId memberId departmentId
     * @return
     */
    @PostMapping ("/boards")
    public ConcurrentHashMap<String, Object> callBoards(@RequestParam(defaultValue = "ALL") String category,
                                                        @RequestParam(defaultValue = "0") int sorted,
                                                        BoardSearchDto boardSearchDto,
                                                        @RequestBody BoardCallDto boardCallDto,
                                                        @PageableDefault(size = 12) Pageable pageable){
        log.info("category [{}], sorted [{}] schoolId[{}] departmentId [{}] memberId [{}] offset[{}] size [{}] column [{}] value [{}]",
                category,
                sorted,
                boardCallDto.getSchoolId(),
                boardCallDto.getDepartmentId(),
                boardCallDto.getMemberId(),
                pageable.getOffset(),
                pageable.getPageSize(),
                boardSearchDto.getColumn(),
                boardSearchDto.getValue());
        checkPagingSize(pageable.getPageSize());
        return boardCallService.getBoardSortedCall(boardCallDto, category, sorted, pageable, boardSearchDto);
    }

    @PostMapping("/board/{boardId}/{memberId}")
    public BoardDto clickBoard(@PathVariable Long boardId, @PathVariable Long memberId, HttpServletRequest request, HttpServletResponse response){
        return boardCallService.getBoardDetail(boardId, memberId,request,response);
    }

    @GetMapping("/board/department/{schoolId}")
    public List<DepartmentCallDto> getDepartments(@PathVariable Long schoolId){
        return departmentFindService.getDepartmentBySchoolId(schoolId);
    }

    private void checkPagingSize(Integer limit) {
        if (limit == 0) {
            throw new PagingLimitEqualsZeroException("limit 의 개수는 1 이상이여야 합니다.");
        }
    }
}
