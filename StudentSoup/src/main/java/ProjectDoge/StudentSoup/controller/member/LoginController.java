package ProjectDoge.StudentSoup.controller.member;

import ProjectDoge.StudentSoup.dto.member.MemberDto;
import ProjectDoge.StudentSoup.dto.member.MemberLoginRequestDto;
import ProjectDoge.StudentSoup.interceptor.SessionConst;
import ProjectDoge.StudentSoup.service.member.MemberLoginService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class LoginController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/login")
    public MemberDto login(@RequestBody MemberLoginRequestDto dto, HttpServletRequest request){

        MemberDto loginDto = memberLoginService.login(dto.getId(), dto.getPwd());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginDto.getMemberClassification());
        session.setMaxInactiveInterval(1800); // 세션의 생명주기 30분으로 고정
        return loginDto;
    }

    @PostMapping("/logout")
    public ResponseEntity<Result<String>> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new Result<String>("ok"));
    }

    @Getter
    static class Result<T> {
        private final T data;

        public Result(T data){
            this.data = data;
        }
    }
}
