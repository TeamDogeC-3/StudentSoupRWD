package ProjectDoge.StudentSoup.entity.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardCategory {

    FREE("자유게시판"),
    EMPLOYMENT("취업게시판"),
    CONSULTING("상담게시판"),
    TIP("팁게시판"),
    ANNOUNCEMENT("공지사항");

    private final String boardCategory;
}
