package ProjectDoge.StudentSoup.dto.restaurant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestaurantSortedCase {

    NORMAL(0),
    STAR(1),
    LIKED(2),
    REVIEW(3),
    DISTANCE(4);


    private final int value;
}
