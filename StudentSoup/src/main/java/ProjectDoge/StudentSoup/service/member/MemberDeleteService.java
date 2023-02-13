package ProjectDoge.StudentSoup.service.member;

import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import ProjectDoge.StudentSoup.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDeleteService {

    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final MemberFindService memberFindService;

    @Transactional
    public void deleteMember(Long memberId){
        Member member = memberFindService.findOne(memberId);
        if(member.getImageFile() != null)
            fileService.deleteFile(member.getImageFile());

        memberRepository.delete(member);
    }
}
