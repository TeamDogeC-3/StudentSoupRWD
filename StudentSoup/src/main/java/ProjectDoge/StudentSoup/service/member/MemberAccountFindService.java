package ProjectDoge.StudentSoup.service.member;

import ProjectDoge.StudentSoup.dto.member.EmailDto;
import ProjectDoge.StudentSoup.dto.member.MemberFindAccountDto;
import ProjectDoge.StudentSoup.entity.member.Member;
import ProjectDoge.StudentSoup.exception.member.MemberEmailNotFoundException;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.exception.member.MemberNotMatchIdEmailException;
import ProjectDoge.StudentSoup.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberAccountFindService {

    @Value("${admin.email}")
    private String adminEmail;

    private final MemberRepository memberRepository;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public EmailDto createFindMemberIdUsingEmail(String email){
        log.info("회원 아이디 찾기 서비스 로직을 실행하였습니다.");
        MemberFindAccountDto findMember = memberRepository.findByAccountUsingEmail(email)
                .orElseThrow(() -> {
                    log.info("이메일이 일치하는 회원을 찾지 못하였습니다.");
                    throw new MemberEmailNotFoundException("등록된 이메일을 찾을 수 없습니다.");
                });
        return createFindIdMailDto(findMember);
    }

    private EmailDto createFindIdMailDto(MemberFindAccountDto findMember){
        log.info("회원 아이디 찾기 메세지 객체 생성이 시작되었습니다. [{}]", findMember.getId());
        EmailDto dto = new EmailDto();
        dto.setEmail(findMember.getEmail());
        dto.setTitle("[takemh] 아이디 찾기 메일입니다.");
        dto.setMessage("안녕하세요. takemh 입니다. [" + findMember.getNickname() + "]님의 아이디는 " + findMember.getId() + "입니다.");
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public EmailDto createFindPwdUsingEmailAndId(String email, String id) {
        log.info("회원 비밀번호 찾기 서비스 로직이 실행되었습니다.");
        MemberFindAccountDto findMember = memberRepository.findByAccountUsingEmailAndId(email, id)
                .orElseThrow(() -> {
                    log.info("아이디와 이메일이 일치하는 회원을 찾지 못하였습니다.");
                    throw new MemberNotMatchIdEmailException("아이디와 이메일이 일치하지 않습니다.");
                });
        return createFindPwdMailDto(findMember);
    }
    private EmailDto createFindPwdMailDto(MemberFindAccountDto findMember){
        memberTempPwdUpdate(findMember);
        log.info("회원 비밀번호 찾기 메세지 객체 생성이 시작되었습니다. [{}]", findMember.getId());
        EmailDto dto = new EmailDto();
        dto.setEmail(findMember.getEmail());
        dto.setTitle("[takemh] 비밀번호 찾기 메일입니다.");
        dto.setMessage("안녕하세요. takemh 입니다. [" + findMember.getNickname() + "]님의 임시 비밀번호는 " + findMember.getPwd() + "입니다." +
                "임시 비밀번호를 복사하여 로그인 하신 후 새로운 비밀번호로 변경 부탁드립니다.");
        return dto;
    }

    private void memberTempPwdUpdate(MemberFindAccountDto findMember){
        log.info("회원 임시 비밀번호 업데이트가 시작되었습니다.");
        Member member = memberRepository.findById(findMember.getId())
                .orElseThrow(() -> {
                    throw new MemberNotFoundException("회원을 찾을 수가 없습니다.");
                });
        String tempPwd = UUID.randomUUID().toString();
        tempPwd = tempPwd.substring(0, tempPwd.indexOf('-'));
        member.setPwd(passwordEncoder.encode(tempPwd));
        memberRepository.save(member);
        findMember.setPwd(tempPwd);
        log.info("변경 된 회원의 비밀번호 : " + member.getPwd());
        log.info("임시 비밀번호 업데이트가 완료되었습니다.");
    }

    public void mailSend(EmailDto dto){
        log.info("회원 아이디/패스워드 찾기 메일을 생성을 시작하였습니다.");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setFrom(adminEmail);
        message.setSubject(dto.getTitle());
        message.setText(dto.getMessage());
        mailSender.send(message);
        log.info("회원 아이디/패스워드 찾기 메일이 전송되었습니다.");
    }


}
