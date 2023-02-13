package ProjectDoge.StudentSoup.service.boardreply;

import ProjectDoge.StudentSoup.exception.boardreply.BoardReplyContentOutOfRangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BoardReplyValidationService {

    public void checkContent(String content) {
        if(content.length() < 2 || content.length() > 500){
            throw new BoardReplyContentOutOfRangeException("댓글의 내용은 2자 이상이거나 500자 이하이여야 합니다.");
        }
    }

}
