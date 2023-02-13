package ProjectDoge.StudentSoup.dto.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardSortedCase {
    NORMAL(0),

    LIKED(1),

    LATEST(2),

    VIEW(3),

    REVIEW(4);

    private final int value;
}
