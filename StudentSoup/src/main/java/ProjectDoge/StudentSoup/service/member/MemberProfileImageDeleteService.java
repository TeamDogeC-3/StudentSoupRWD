package ProjectDoge.StudentSoup.service.member;

import ProjectDoge.StudentSoup.dto.member.MemberDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.repository.file.FileRepository;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberProfileImageDeleteService {

    private final  MemberFindService memberFindService;
    private final FileService fileService;

    private final FileRepository fileRepository;

    private final MemberRepository memberRepository;
    @Transactional
    public MemberDto deleteImage(Long memberId) {
        Member member = memberFindService.findOne(memberId);
        if(member.getImageFile() != null) {
            fileService.deleteFile(member.getImageFile());
            fileRepository.delete(member.getImageFile());
            member.setImageFile(null);
            memberRepository.save(member);
        }
        MemberDto memberDto = new MemberDto().getMemberDto(member);

        return memberDto;
    }
}
