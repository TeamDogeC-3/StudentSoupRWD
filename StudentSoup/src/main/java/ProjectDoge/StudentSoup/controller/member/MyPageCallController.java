package ProjectDoge.StudentSoup.controller.member;

import ProjectDoge.StudentSoup.dto.member.*;
import ProjectDoge.StudentSoup.service.member.MemberMyPageCallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageCallController {

    private final MemberMyPageCallService memberMyPageCallService;

    @GetMapping
    public MemberMyPageDto callMyPageMain(@RequestBody MemberMyPageRequestDto dto) {
        return memberMyPageCallService.callMyPageMain(dto.getMemberId());
    }

    @PostMapping("/detail")
    public MemberMyPageDetailDto callMyPageDetail(@RequestBody MemberMyPageRequestDto dto){
        return memberMyPageCallService.callMyPageDetail(dto.getMemberId());
    }

    @PostMapping("/board")
    public Page<MemberMyPageBoardDto> callMyPageBoard(
            @RequestBody MemberMyPageRequestDto dto,
            @PageableDefault(size = 6) Pageable pageable
    ) {
        return memberMyPageCallService.callMyPageBoard(dto.getMemberId(), pageable);
    }

    @PostMapping("/boardReply")
    public Page<MemberMyPageBoardReplyDto> callMyPageBoardReply(
            @RequestBody MemberMyPageRequestDto dto,
            @PageableDefault(size = 6) Pageable pageable
    ) {
        return memberMyPageCallService.callMyPageBoardReview(dto.getMemberId(), pageable);
    }

    /**
     *
     * @param cond today(오늘), month(한달), halfYear(6개월), year(1년)
     */
    @PostMapping("/restaurantReview")
    public Page<MemberMyPageRestaurantReviewDto> callMyPageRestaurantReview(
            @RequestBody MemberMyPageRequestDto dto,
            @PageableDefault(size = 6) Pageable pageable,
            @RequestParam(value = "filter", required = false) String cond
    ) {
        return memberMyPageCallService.callMyRestaurantReview(dto.getMemberId(), cond, pageable);
    }
}
