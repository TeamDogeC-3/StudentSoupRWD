package ProjectDoge.StudentSoup.service.member;

import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.exception.member.MemberIdNotSentException;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberFindService {

    private final MemberRepository memberRepository;

    public Member findOne(Long memberId) {
        checkMemberIdSent(memberId);
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new MemberNotFoundException("회원을 찾지 못하였습니다.");
                });
    }

    private void checkMemberIdSent(Long memberId) {
        if(memberId == null){
            log.info("memberId가 전송되지 않았습니다.");
            throw new MemberIdNotSentException("memberId가 전송되지 않았습니다.");
        }
    }
}
