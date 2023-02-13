package ProjectDoge.StudentSoup.controller.member;

import ProjectDoge.StudentSoup.dto.member.MemberDto;
import ProjectDoge.StudentSoup.service.member.MemberProfileImageDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileDeleteController {

    private final MemberProfileImageDeleteService memberProfileImageDeleteService;

    @DeleteMapping("members/delete/image")
    public MemberDto deleteProfile(@RequestParam(value = "memberId") Long memberId){
        log.info("받은 memberId : [{}]",memberId);
        MemberDto dto = memberProfileImageDeleteService.deleteImage(memberId);

        return dto;
    }
}
