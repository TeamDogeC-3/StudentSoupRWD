package ProjectDoge.StudentSoup.controller.board;

import ProjectDoge.StudentSoup.dto.board.BoardCategoryDto;
import ProjectDoge.StudentSoup.dto.board.BoardFormDto;
import ProjectDoge.StudentSoup.dto.department.DepartmentCallDto;
import ProjectDoge.StudentSoup.service.board.BoardCallService;
import ProjectDoge.StudentSoup.service.board.BoardResisterService;
import ProjectDoge.StudentSoup.service.department.DepartmentFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardCreateController {

    private final BoardResisterService boardResisterService;
    private final DepartmentFindService departmentFindService;

    @PutMapping(value = "/board/{memberId}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ConcurrentHashMap<String,Object> createBoard(@PathVariable Long memberId,
                                                        BoardFormDto boardFormDto, HttpServletRequest request){
        ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<>();
        boardFormDto.setIp(request.getRemoteAddr());
        Long boardId = boardResisterService.join(memberId, boardFormDto, boardFormDto.getMultipartFileList());
        resultMap.put("boardId",boardId);
        resultMap.put("result","ok");
        return resultMap;
    }
    @GetMapping("/board/create/{memberId}/{schoolId}")
    public ConcurrentHashMap<String,Object> getMemberClassificationList(@PathVariable Long memberId, @PathVariable Long schoolId ){
        ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<>();
        List<BoardCategoryDto> memberClassificationList = boardResisterService.getMemberClassification(memberId);
        List<DepartmentCallDto> departments = departmentFindService.getDepartmentBySchoolId(schoolId);
        resultMap.put("category",memberClassificationList);
        resultMap.put("departments",departments);

        return resultMap;
    }
}
