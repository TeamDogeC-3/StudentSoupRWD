package ProjectDoge.StudentSoup.controller.member;

import ProjectDoge.StudentSoup.dto.member.MemberDto;
import ProjectDoge.StudentSoup.dto.member.MemberUpdateDto;
import ProjectDoge.StudentSoup.dto.member.MemberUpdateValidationDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.service.member.MemberFindService;
import ProjectDoge.StudentSoup.service.member.MemberUpdateService;
import ProjectDoge.StudentSoup.service.member.MemberValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberUpdateController {
    private final MemberValidationService memberValidationService;
    private final MemberFindService memberFindService;
    private final MemberUpdateService memberUpdateService;

    @PostMapping("/edit/{memberId}/validation")
    public ResponseEntity<ConcurrentHashMap<String, String>> editIdCheck(@PathVariable("memberId") Long memberId,
                                                         @Validated @RequestBody MemberUpdateValidationDto dto,
                                                         BindingResult bindingResult){
        ConcurrentHashMap<String, String> resultMap = new ConcurrentHashMap<>();
        Member member = memberFindService.findOne(dto.getMemberId());
        memberValidationService.validationCoincideMemberIdPwd(member, dto.getPwd());
        resultMap.put("result", "ok");
        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/edit/{memberId}")
    public MemberDto editMember(@PathVariable("memberId") Long memberId,
                                @RequestBody MemberUpdateDto dto){
        Long updatedMemberId = memberUpdateService.updateMember(dto);
        Member member = memberFindService.findOne(updatedMemberId);
        MemberDto memberDto = new MemberDto().getMemberDto(member);
        return memberDto;
    }

}
