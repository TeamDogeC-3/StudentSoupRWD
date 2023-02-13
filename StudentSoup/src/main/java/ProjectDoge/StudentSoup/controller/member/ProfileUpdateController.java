package ProjectDoge.StudentSoup.controller.member;

import ProjectDoge.StudentSoup.dto.member.MemberDto;
import ProjectDoge.StudentSoup.service.member.MemberProfileImageUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileUpdateController {

    private final MemberProfileImageUpdateService memberProfileImageUpdateService;

    @PostMapping(value = "/members/edit/image", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public MemberDto updateProfile(@RequestParam(value = "memberId") Long memberId, @RequestPart MultipartFile multipartFile){
        log.info("받은 memberId : [{}], imageFile : [{}]", memberId, multipartFile.getOriginalFilename());
        MemberDto dto = memberProfileImageUpdateService.memberProfileUpdate(memberId, multipartFile);
        return dto;
    }
}
